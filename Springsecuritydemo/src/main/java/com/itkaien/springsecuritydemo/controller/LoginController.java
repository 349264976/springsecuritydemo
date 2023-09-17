package com.itkaien.springsecuritydemo.controller;

import com.itkaien.springsecuritydemo.domain.ResponseResult;
import com.itkaien.springsecuritydemo.domain.User;
import com.itkaien.springsecuritydemo.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/user/login")
    @ApiOperation("登录校验")
    public ResponseResult login(@RequestBody @ApiParam("校验得用户信息") User user) {
        //登录
        return  loginService.login(user);

    }


    @GetMapping("/user/logout")
    public ResponseResult logout(){

        return loginService.logout();
    }



}
