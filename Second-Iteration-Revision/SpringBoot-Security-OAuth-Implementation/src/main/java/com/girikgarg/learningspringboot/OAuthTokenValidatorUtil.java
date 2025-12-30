package com.girikgarg.learningspringboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Map;

@Component
public class OAuthTokenValidatorUtil {
    public String isTokenValid(String accessToken) {
        // once we know the issuer, we get the public key to verify authenticity of JWT token and then decode it
        String iss = getIssuerIdFromToken(accessToken);

        if (iss == null) {
            return null;
        }
        
        JwtDecoder decoder = JwtDecoders.fromIssuerLocation(iss);
        Jwt jwt = decoder.decode(accessToken);
        if (jwt != null) {
            return (String) jwt.getClaims().get("sub");
        }
        return null;
    }

    // Get issuer of JWT Token like "Gitlab", "Auth0", "Google" etc
    public static String getIssuerIdFromToken(String jwtToken) {
        try {
            String[] parts = jwtToken.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid JWT Token");
            }
            
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payloadMap = mapper.readValue(payloadJson, Map.class);
            return (String) payloadMap.get("iss");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
