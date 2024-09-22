package com.example.cypherserverside.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cypherserverside.entity.Result;
import com.example.cypherserverside.entity.User;
import com.example.cypherserverside.entity.dto.UserDto;
import com.example.cypherserverside.mapper.UserMapper;
import com.example.cypherserverside.service.UserService;
import com.example.cypherserverside.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.cypherserverside.utils.ErrorMessage.*;
import static com.example.cypherserverside.utils.JwtUtils.GeneateToken;
import static com.example.cypherserverside.utils.SaltMD5Utils.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Result register(String username, String password) {

        User user = new User();
        user.setUserName(username);
        user.setSalt(generateSalt());
        user.setSaltPassword(generateSaltPassword(password, user.getSalt()));
        user.setRole(0);
        user.setLinkHosts(new ArrayList<>(){});
        QueryWrapper<UserDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDto userDto = userMapper.selectOne(queryWrapper);
        if (userDto != null) {
            return Result.fail(500,USER_ALREADY_EXIST_MSG);
        }
        userDto = new UserDto(user);
        int result = userMapper.insert(userDto);
        if (result == 0) {
            return Result.fail(500,WRITE_USER_FAILURE_MSG);
        }
        return Result.ok();
    }

    @Override
    public Result login(String username, String password) {
        QueryWrapper<UserDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDto userDto = userMapper.selectOne(queryWrapper);
        User user = userDto.toEntity();
        if (userDto == null) {
            return Result.fail(500,NOT_FOUND_USER);
        }
        if (validateSaltPassword(password, userDto.getSalt(), userDto.getSaltPassword())){
            return Result.fail(500,BAD_PASSWORD_MSG);
        }
        String token = UUID.randomUUID().toString();
        //token错误
        //String token = GeneateToken(user);
        redisUtil.saveToken(username,token);
        return Result.ok(token);
    }
}
