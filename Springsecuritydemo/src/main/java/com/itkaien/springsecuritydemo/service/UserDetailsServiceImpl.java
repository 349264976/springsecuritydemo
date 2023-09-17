package com.itkaien.springsecuritydemo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.itkaien.springsecuritydemo.domain.LoginUser;
import com.itkaien.springsecuritydemo.domain.User;
import com.itkaien.springsecuritydemo.mapper.MenuMapper;
import com.itkaien.springsecuritydemo.mapper.UserMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, username));

        //如果没有对应的用户就抛出异常信息

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User Not Found");
        }
        //查询到用户封装成userdetials对象
        List<String> strings = mapper.selectPermsByUserid(user.getId());
        //TODO 查询对应的权限信息
        List<String> list =new ArrayList<String>(Arrays
                .asList("test", "admin"));


        return new LoginUser(user,strings);


    }
}
