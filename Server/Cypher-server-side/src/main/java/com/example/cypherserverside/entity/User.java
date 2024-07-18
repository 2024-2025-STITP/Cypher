package com.example.cypherserverside.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Data
@Setter
@Getter
@AllArgsConstructor
public class User {
    private Integer userId;

    @Range(message = "最少1个字符，最多30个字符",min = 1, max = 40)
    private String userName;

    private String email;

    @Range(message = "最少要6个字符，最多40个字符", min = 6, max = 40)
    private String password;

    private List<Host> linkHosts = new LinkedList<Host>();

    private Host myHost;

}
