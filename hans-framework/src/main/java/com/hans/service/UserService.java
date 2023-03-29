package com.hans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hans.commen.ResponseResult;
import com.hans.dto.UserDto;
import com.hans.entity.User;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-26 19:56:31
 */
public interface UserService extends IService<User> {

    ResponseResult login(UserDto userDto);

    ResponseResult logout();

    ResponseResult getUserInfo();
}

