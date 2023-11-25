package com.heima.user.controller;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.dtos.RegisterDto;
import com.heima.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApUserController {
    @Autowired
    private ApUserService apUserService;

    @PostMapping("/login/login_auth")
    public ResponseResult login(@RequestBody LoginDto loginDto){
        return apUserService.login(loginDto);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterDto registerDto){
        return apUserService.register(registerDto);
    }
}
