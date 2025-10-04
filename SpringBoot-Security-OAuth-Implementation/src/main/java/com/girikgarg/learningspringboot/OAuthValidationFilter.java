package com.girikgarg.learningspringboot;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


public class OAuthValidationFilter extends OncePerRequestFilter {
    private final OAuthTokenValidatorUtil oauthTokenValidatorUtil;

    public OAuthValidationFilter(OAuthTokenValidatorUtil oauthTokenValidatorUtil) {
        this.oauthTokenValidatorUtil = oauthTokenValidatorUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractJwtFromRequest(request);
        if (token != null) {
            String userName = oauthTokenValidatorUtil.isTokenValid(token);
            if (StringUtil.isNullOrEmpty(userName)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return ;
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(userName, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
