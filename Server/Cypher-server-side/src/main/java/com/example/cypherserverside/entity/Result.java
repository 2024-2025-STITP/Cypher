package com.example.cypherserverside.entity;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer stateCode;
    private Boolean success;
    private String message;
    private Object data;
    private Long total;

    public static Result ok(){
        return new Result(HttpStatus.HTTP_OK,true, null, null, null);
    }
    public static Result ok(Object data){
        return new Result(HttpStatus.HTTP_OK,true, null, data, null);
    }
    public static Result ok(List<?> data, Long total){
        return new Result(HttpStatus.HTTP_OK,true, null, data, total);
    }
    public static Result fail(Integer stateCode,String errorMsg){
        return new Result(stateCode,false, errorMsg, null, null);
    }
}