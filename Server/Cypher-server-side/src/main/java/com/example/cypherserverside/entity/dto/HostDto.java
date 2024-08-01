package com.example.cypherserverside.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HostDto {
    private String hostName;
    private String hostIp;
    private Integer hostCode;
}
