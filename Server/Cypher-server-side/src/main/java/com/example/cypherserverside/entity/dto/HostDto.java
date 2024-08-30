package com.example.cypherserverside.entity.dto;

import com.example.cypherserverside.utils.JwtUtils;
import com.example.cypherserverside.utils.SaltMD5Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

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
