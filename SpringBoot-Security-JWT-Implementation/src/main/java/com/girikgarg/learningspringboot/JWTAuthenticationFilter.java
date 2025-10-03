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

// not a bean, to be noted
// filter should be called only once per request
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().equals("/generate-token")) {
            filterChain.doFilter(request, response);
            /**
             * if the request endpoint is not /generate-token, call the other filters in the filter chain
             * no need of calling the jwt authentication filter
             */
            return ;
        }
        ObjectMapper mapper = new ObjectMapper();
        LoginRequest loginRequest = mapper.readValue(request.getReader(), LoginRequest.class);

        // DAOAuthenticationProvider supports UsernamePasswordAuthenticationToken, hence creating an object of it
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        // Internally, the authentication manager will iterate over the list of authentication providers and check which auth provider supports this authentication object
        // DAOAuthenticationProvider will support this authentication object and authentication manager will delegate the responsibility to it
        Authentication authResult = authenticationManager.authenticate(authToken);

        if (authResult.isAuthenticated()) {
            String token = jwtUtil.generateToken(loginRequest.getUsername(), 10); // 10 min expiry time
            response.setHeader("Authorization", "Bearer " + token);
        }

        String refreshToken = jwtUtil.generateToken(authResult.getName(), 7* 24* 60); // 7 days long lived refresh token

        // Set refresh token in Http Only Cookie
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true); // prevent javascript from accessing it
        refreshCookie.setSecure(true); // sent only over HTTPS
        refreshCookie.setPath("/refresh-token"); // only for this endpoint, the cookie will be appended
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days expiry
        response.addCookie(refreshCookie);
    }
}

