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

        String issuer = getIssuerFromToken(accessToken);
        JwtDecoder decoder = JwtDecoders.fromIssuerLocation(issuer);
        Jwt jwt = decoder.decode(accessToken);
        if (jwt!=null) {
            return (String) jwt.getClaims().get("sub");
        }
        return null;
    }

    public static String getIssuerFromToken(String jwtToken) {
        try {
            String [] parts = jwtToken.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token");
            }
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1])); // the payload part of JWT token
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payloadMap = mapper.readValue(payloadJson, Map.class);
            return (String) payloadMap.get("iss");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
