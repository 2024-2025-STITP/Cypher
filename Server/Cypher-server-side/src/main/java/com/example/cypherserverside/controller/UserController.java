package com.example.cypherserverside.controller;

import com.example.cypherserverside.entity.Result;
import com.example.cypherserverside.entity.UserDateForm;
import com.example.cypherserverside.service.UserService;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;

import static com.example.cypherserverside.utils.ErrorMessage.BAD_REQUEST_MSG;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@RequestBody UserDateForm userDateForm) {
        String username = userDateForm.getUsername();
        String password = userDateForm.getPassword();
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)){
            return Result.fail(400,BAD_REQUEST_MSG);
        }
        return userService.register(userDateForm.getUsername(), userDateForm.getPassword());
    }
    @PostMapping("/login")
    public Result login(@RequestBody UserDateForm userDateForm) {
        if (userDateForm == null) {
            return Result.fail(400, BAD_REQUEST_MSG);
        }
        String username = userDateForm.getUsername();
        String password = userDateForm.getPassword();

        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return Result.fail(400, BAD_REQUEST_MSG);
        }
        return userService.login(username, password);
//        try {
//            return userService.login(username, password);
//        } catch (Exception e) {
//            return Result.fail(500, "登录过程中发生错误");
//        }
    }
}
