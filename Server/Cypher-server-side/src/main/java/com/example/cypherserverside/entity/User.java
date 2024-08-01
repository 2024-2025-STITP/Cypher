package com.example.cypherserverside.entity;

import com.example.cypherserverside.utils.SaltMD5Utils;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor
public class User {
    private Integer userId;
    @Range(message = "最少1个字符，最多30个字符",min = 1, max = 40)
    private String userName;
    @Max(100)
    private String email;

    private String salt;
    private String saltPassword;

    private List<Host> linkHosts;

    public void setSaltPassword(String password) {
        this.salt = SaltMD5Utils.generateSalt();
        this.saltPassword = SaltMD5Utils.generateSaltPassword(salt + password, salt);
    }
}
