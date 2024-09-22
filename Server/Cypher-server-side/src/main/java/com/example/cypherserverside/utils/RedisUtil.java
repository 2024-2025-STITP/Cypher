package com.example.cypherserverside.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.example.cypherserverside.utils.RedisConstants.USER_TOKEN_EXPIRE_TIME;
import static com.example.cypherserverside.utils.RedisConstants.USER_TOKEN_KEY;


@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 保存Token到Redis
    public void saveToken(String username, String token) {
        redisTemplate.opsForValue().set(USER_TOKEN_KEY + username, token, USER_TOKEN_EXPIRE_TIME, TimeUnit.MINUTES); // 1小时有效期
    }

    // 从Redis中获取Token
    public String getToken(String username) {
        return redisTemplate.opsForValue().get("TOKEN_" + username);
    }

    // 删除Token
    public void deleteToken(String username) {
        redisTemplate.delete("TOKEN_" + username);
    }
}
