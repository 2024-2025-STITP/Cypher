package com.example.cypherserverside.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cypherserverside.entity.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDto> {
}
