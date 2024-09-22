package com.example.cypherserverside.utils;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

@Component
public class RedisConstants {
    public static final String LOGIN_USER_KEY = "login_user_key:";
    public static final String USER_TOKEN_KEY = "user_token_key:";
    public static final Long USER_TOKEN_EXPIRE_TIME = 360L+ RandomUtil.randomLong(0,100L);;
}
