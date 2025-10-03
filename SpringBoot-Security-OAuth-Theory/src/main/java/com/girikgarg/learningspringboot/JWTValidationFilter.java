package com.girikgarg.learningspringboot;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// not a bean, to be noted
// filter should be called only once per request
public class JWTValidationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = extractJwtFromRequest(request);
        if (token != null) {
            JWTAuthenticationToken authenticationToken = new JWTAuthenticationToken(token);
            // this authentication token is supported by JWTAuthenticationProvider, Authentication Manager will delegate the responsibility to it
            Authentication authResult = authenticationManager.authenticate(authenticationToken);
            if (authResult.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authResult);
            }
        }

        filterChain.doFilter(request, response); // call the further filters (if any)
    }


    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken !=null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
