package com.hans.securityAll;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hans.Constants.RedisConstants;
import com.hans.commen.ResponseResult;
import com.hans.entity.User;
import com.hans.units.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");


        if(StrUtil.isBlank(token)){
            //说明该接口不需要登录  直接放行
            filterChain.doFilter(request,response);
            return;
        }

        //解析获取userid
        String userId = null;
        try {
            Object id1 = Jwt.JwtVerify(token);
            userId = id1.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            ResponseResult.fail(411,"请重新登录");
            return;
        }

        //从redis中获取用户信息
        String userMessage = stringRedisTemplate.opsForValue().get(RedisConstants.LOGINID_KEY + userId);
        User user = JSONUtil.toBean(userMessage, User.class);


        //如果获取不到
        if (StrUtil.isBlankIfStr(user)){
            //重新登录
            return;
        }
//        存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }
}
