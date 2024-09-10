package com.example.cypherserverside.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Message<T>{
    private T data;
    private int code;
    private String message;
}
