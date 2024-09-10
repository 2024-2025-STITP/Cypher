package com.example.cypherserverside.entity;

import lombok.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VirtualHost {
    private Integer hostId;
    private String hostName;
    private String hostIP;
    private Boolean hostStatus = Boolean.FALSE;
    private String byTheHostIp;
}
