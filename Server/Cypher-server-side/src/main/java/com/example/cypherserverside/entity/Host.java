package com.example.cypherserverside.entity;

import ch.qos.logback.core.util.SystemInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
public class Host {
    private Integer hostId;

    private String hostName;

    private String hostIP;

    private String macAddr;

    private List<VirtualHost> vHosts = new LinkedList<>();

    private Boolean hostStatus;                 //表示是否能被监控

}
