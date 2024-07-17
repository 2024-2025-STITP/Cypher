package com.example.cypherserverside.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class VirtualHost {
    private Integer hostId;

    private String hostName;

    private String hostPassword;

    private String hostIp;

    private Boolean hostStatus;
}
