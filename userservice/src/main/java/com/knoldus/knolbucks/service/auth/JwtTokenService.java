package com.knoldus.knolbucks.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.knoldus.knolbucks.model.User;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtTokenService {

    private static final String TOKEN_SECRET = "s4T2zOIWHMM1sxq";
    private static Algorithm algorithm;

    static {
        try {
            algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String createToken(User user) {
        try {
            String token = JWT.create()
                    .withClaim("userId", user.get_id().toString())
                    .withClaim("createdAt", System.currentTimeMillis())
                    .withClaim("role", user.getRole().toString())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            //log Token Signing Failed
        }
        return null;
    }

    public String getUserIdFromToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            //log Token Verification Failed
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        String userId = this.getUserIdFromToken(token);
        return userId != null;
    }
}
