package com.example.cypherserverside.utils;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.validator.constraints.Length;

import java.security.MessageDigest;
import java.util.Random;

public class SaltMD5Utils {

    private static final Integer saltLength = 8;

    //生成salt
    public static String generateSalt(){
        Random rand = new Random();

        StringBuilder stringBuilder = new StringBuilder(saltLength);
        stringBuilder.append(rand.nextInt(9999)).append(rand.nextInt(9999));

        Integer length = stringBuilder.length();
        for(int i = 0; i < saltLength - length; i++){
            stringBuilder.append(rand.nextInt(9));
        }
        return stringBuilder.toString();
    }

    //生成salt password
    public static String generateSaltPassword(String password, String salt){
        password = md5Hex(password);

        int length = password.length() * 2;
        char[] cs = new char[length];
        for(int i = 0; i < length; i += 2){
            cs[i] = password.charAt(i / 2);
            cs[i + 1] = salt.charAt((i / 2) % saltLength);
        }
        return new String(cs);
    }

    //输入的明文和salt密码验证
    public static Boolean validateSaltPassword(String password, String salt, String saltPassword){
        password = md5Hex(password + salt);

        int length = password.length() * 2;
        char[] cs = new char[length];
        for(int i = 0; i < length; i += 2){
            cs[i] = password.charAt(i / 2);
            cs[i + 1] = salt.charAt((i / 2) % saltLength);
        }
        return saltPassword.equals(new String(cs));
    }

    //(不使用)加密明文和盐混合
    private static String md5Hex(String str){
        String result = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            result = new String(new Hex().encode(bs));

        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return result;
    }
}
