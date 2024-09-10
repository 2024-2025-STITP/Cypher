package com.example.cypherserverside.service.impl;

import com.example.cypherserverside.service.user.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public String register(String username, String email, String password) {
        return "";
    }

    @Override
    public String unregister(String token, String password) {
        return "";
    }

    @Override
    public String quit() {
        return "";
    }
}
