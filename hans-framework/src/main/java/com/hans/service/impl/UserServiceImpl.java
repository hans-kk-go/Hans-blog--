package com.hans.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hans.Constants.RedisConstants;
import com.hans.commen.ResponseResult;
import com.hans.dao.UserDao;
import com.hans.dto.UserDto;
import com.hans.entity.User;
import com.hans.securityAll.SecurityLoginUser;
import com.hans.service.UserService;
import com.hans.units.Jwt;
import com.hans.vo.LoginUserVo;
import com.hans.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-26 19:56:31
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    //security新加代码
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ResponseResult login(UserDto userDto) {


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        if (BeanUtil.isEmpty(authenticate)){
            return ResponseResult.fail(409,"用户名或密码错误");
        }

        SecurityLoginUser LogUser = (SecurityLoginUser)authenticate.getPrincipal();
        User user = LogUser.getUser();

        //存入redis
        String UserJson = JSONUtil.toJsonStr(user);
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGINID_KEY + user.getId(),UserJson);


        String token = Jwt.JwtCreate(user.getId());
        UserInfoVo userInfoVo = BeanUtil.toBean(user, UserInfoVo.class);
        LoginUserVo loginUserVo = new LoginUserVo(token, userInfoVo);
        return ResponseResult.ok("操作成功",loginUserVo);



    }

    @Override
    public ResponseResult logout() {
        //获取userid从securitycontext中
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = user.getId();

        //删除redis中的对应数据
        Boolean delete = stringRedisTemplate.delete(RedisConstants.LOGINID_KEY + id);
        if(delete){
            return ResponseResult.ok("退出成功");
        }
        return ResponseResult.fail(430,"退出失败");


    }

    @Override
    public ResponseResult getUserInfo() {
        List<User> list = list();
        List<UserInfoVo> userInfoVos = BeanUtil.copyToList(list, UserInfoVo.class);
        return ResponseResult.ok(userInfoVos);
    }
}
