package com.example.cypherserverside.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStringConverter {

    // 将 List<String> 转换为 String
    public static String listToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        // 使用 String.join() 方法将 List 中的元素连接成一个字符串
        return String.join(",", list);
    }

    // 将 String 转换为 List<String>
    public static List<String> stringToList(String str) {
        if (str == null || str.isEmpty()) {
            return new ArrayList<>();
        }
        // 使用 Arrays.asList() 方法将字符串按逗号分割并转换为 List
        return new ArrayList<>(Arrays.asList(str.split(",")));
    }
}