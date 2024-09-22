package com.example.cypherserverside.entity;

import com.example.cypherserverside.utils.SaltMD5Utils;
import com.sun.jna.WString;
import jakarta.validation.constraints.Max;
import lombok.*;
import org.hibernate.validator.constraints.Range;


import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;


@Data
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    @Range(message = "最少1个字符，最多30个字符",min = 1, max = 40)
    private String userName;
    @Max(100)
    private String email;

    private String salt;
    private String saltPassword;
    private int  role;//0为游客，1为管理员

    private List<Host> linkHosts = new ArrayList<>();

    public void setSaltPassword(String password) {
        this.salt = SaltMD5Utils.generateSalt();
        this.saltPassword = SaltMD5Utils.generateSaltPassword(salt + password, salt);
    }

    public void addHost(String name, String ip){
        Host host = new Host();
        host.setHostName(name);
        host.setHostIP(ip);
        linkHosts.add(host);
    }

    public boolean removeHost(String ip){
        Host host = linkHosts.stream().filter(h -> h.getHostIP().equals(ip))
                .findFirst().orElse(null);
        if(host == null){
            return false;
        }
        linkHosts.remove(host);
        return true;
    }
}
