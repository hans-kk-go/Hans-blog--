package com.hans.units;


import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;

public class Jwt {

    public static String JwtCreate(Object obj) {
        DateTime now = DateTime.now();
        //加100分钟
        DateTime newTime = now.offsetNew(DateField.MINUTE, 100);

        Map<String,Object> payload = new HashMap<String,Object>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put("userid", obj);


        String key = "hans";
        String token = JWTUtil.createToken(payload, key.getBytes());
        return token;
    }


    public static Object JwtVerify(String token) {
        String key = "hans";
        JWT jwt = JWTUtil.parseToken(token);

        return jwt.getPayloads().getStr("userid");

    }





}


