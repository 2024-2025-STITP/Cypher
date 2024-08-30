package com.example.cypherserverside.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cypherserverside.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Component
public class JwtUtils {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private static String issuer;
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    private static String secretKey;


    //生成Token
    public static String GeneateToken(User user)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return JWT.create().withAudience(user.getUserId().toString())
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(calendar.getTime())
                .withClaim("userName", user.getUserName())
                .withClaim("userEmail",user.getEmail())
                .withClaim("role", user.getRole().getRoleName())
                .sign(Algorithm.HMAC256(secretKey));
    }

    //验证Token合理性,secret传的是用户的id
    public static void validateToken(String token, String secret) throws JWTVerificationException{
        DecodedJWT jwt = null;
        try{
            JWTVerifier verify = JWT.require(Algorithm.HMAC256(secret + "cypher user id")).build();
            jwt = verify.verify(token);
        }catch(Exception e)
        {
            throw new JWTVerificationException("JWT validate failed");
        }
    }

    //获取payload
    public static Map<String, Claim> getTokenObject(String token){
        DecodedJWT jwt = JWT.decode(token);
        Map<String, Claim>  claims = jwt.getClaims();
        return claims;
    }
}
