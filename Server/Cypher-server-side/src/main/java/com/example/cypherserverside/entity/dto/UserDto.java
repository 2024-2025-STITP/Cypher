package com.example.cypherserverside.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.cypherserverside.entity.Host;
import com.example.cypherserverside.entity.User;
import com.example.cypherserverside.utils.ListStringConverter;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

import static com.example.cypherserverside.utils.ListStringConverter.listToString;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserDto {
    @TableId(value = "user_id")
    private Integer userId;
    @TableField("username")
    private String userName;
    @TableField("email")
    private String email;
    @TableField("salt")
    private String salt;
    @TableField("salt_password")
    private String saltPassword;
    @TableField("role")
    private int role;
    @TableField("link_hosts")
    private String linkHosts;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.salt = user.getSalt();
        this.saltPassword = user.getSaltPassword();
        this.role = user.getRole();
        List<String> linkHostList = new ArrayList<>();
        for (Host host : user.getLinkHosts()) {
            linkHostList.add(host.getHostId().toString());
        }
        this.linkHosts =listToString(linkHostList);
    }

    public User toEntity() {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setEmail(email);
        user.setSalt(salt);
        user.setSaltPassword(saltPassword);
        //string转为host的list
        user.setRole(role);
        return user;
    }
}

