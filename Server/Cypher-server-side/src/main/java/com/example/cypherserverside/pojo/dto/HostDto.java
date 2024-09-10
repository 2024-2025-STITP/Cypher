package com.example.cypherserverside.pojo.dto;

import com.example.cypherserverside.utils.SaltMD5Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class HostDto {
    private String hostName;
    private String hostIp;
    private String secretCode;

    HostDto(String hostName, String hostIp) {
        this.hostName = hostName;
        this.hostIp = hostIp;
    }

    public void setHostCode() {
        secretCode = SaltMD5Utils.generateSalt();
    }
}
