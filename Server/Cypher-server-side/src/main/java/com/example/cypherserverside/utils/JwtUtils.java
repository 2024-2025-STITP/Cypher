package com.example.cypherserverside.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cypherserverside.entity.User;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Calendar;
import java.util.Date;

public class JwtUtils {

    //生成Token
    public static String GeneateToken(User user)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return JWT.create().withAudience(user.getUserId().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(calendar.getTime())
                .withClaim("userName", user.getUserName())
                .withClaim("userEmail",user.getEmail())
                .sign(Algorithm.HMAC256(user.getUserId() + "cypher user id"));
    }

    //验证Token合理性,secret传的是用户的id
    public static void verifyToken(String token, String secret) throws JWTVerificationException{
        DecodedJWT jwt = null;
        try{
            JWTVerifier verify = JWT.require(Algorithm.HMAC256(secret + "cypher user id")).build();
            jwt = verify.verify(token);
        }catch(Exception e)
        {
            throw new JWTVerificationException("JWT verification failed");
        }
    }

    //获取签发对象（id）
    public static String getAudience(String token) throws JWTDecodeException {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        }catch (Exception e){
            throw new JWTDecodeException("Invalid JWT token");
        }
        return audience;
    }
}
