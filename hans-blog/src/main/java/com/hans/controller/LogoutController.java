package com.hans.controller;

import com.hans.commen.ResponseResult;
import com.hans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LogoutController {

    @Autowired
    private UserService userService;

    @PostMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }
}
