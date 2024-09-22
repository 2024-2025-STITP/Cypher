package com.example.cypherserverside.service;


import com.example.cypherserverside.entity.Result;

public interface UserService {
    public Result register(String username, String password);
    public Result login(String username, String password);
}
