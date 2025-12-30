package com.girikgarg.learningspringboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         // if the path is not "/generate-token", call the next set of filters. This filter only concerns if request path is "/generate-token"
        if (!request.getServletPath().equals("/generate-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        ObjectMapper objctMapper = new ObjectMapper();
        LoginRequest loginRequest = objctMapper.readValue(request.getInputStream(), LoginRequest.class);

        // UsernamePasswordAuthenticationToken is being specifically used here so that DaoAuthenticationProvider can handle it
        UsernamePasswordAuthenticationToken authToken = 
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        
        Authentication authResult = authenticationManager.authenticate(authToken);
        
        if (authResult.isAuthenticated()) {
            String token = jwtUtil.generateToken(authResult.getName(), 15); // 15 min
            response.setHeader("Authorization", "Bearer " + token);
            
            String refreshToken = jwtUtil.generateToken(authResult.getName(), 7 * 24 * 60);
            Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
            refreshCookie.setHttpOnly(true); // preventing javascript from accessing it
            refreshCookie.setSecure(true); // sent only over HTTPS
            refreshCookie.setPath("/refresh-token"); // cookie sent only for refresh endpoint
            refreshCookie.setMaxAge(7 * 24 * 60 *60); // 7 days expiry
            response.addCookie(refreshCookie);
        }
    }
}
