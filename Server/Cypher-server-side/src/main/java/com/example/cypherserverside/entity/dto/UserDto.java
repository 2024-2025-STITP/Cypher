package com.example.cypherserverside.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String password;
}
