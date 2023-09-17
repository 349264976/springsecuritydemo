package com.itkaien.springsecuritydemo.service;

import com.itkaien.springsecuritydemo.domain.ResponseResult;
import com.itkaien.springsecuritydemo.domain.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
