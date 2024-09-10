package com.example.cypherserverside.pojo;

import lombok.*;

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
