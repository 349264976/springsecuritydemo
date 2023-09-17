package com.itkaien.springsecuritydemo.service.impl;

import com.itkaien.springsecuritydemo.domain.LoginUser;
import com.itkaien.springsecuritydemo.domain.ResponseResult;
import com.itkaien.springsecuritydemo.domain.User;
import com.itkaien.springsecuritydemo.service.LoginService;
import com.itkaien.springsecuritydemo.utils.JwtUtil;
import com.itkaien.springsecuritydemo.utils.RedisCache;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager authentication方法进行用户认证
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                (user.getUserName(), user.getPassword()));
        //如果没有认证通过 给出对应的提示
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登陆失败");
        }
//       如果认证成功了根据用户信息生成jwt
        LoginUser principal = (LoginUser)authenticate.getPrincipal();
        Long id = principal.getUser().getId();

        String jwt = JwtUtil.createJWT(id.toString());
        //把完整的用户存入redis userid作为key token：

        Map<String,String> map=new HashMap<String,String>();

        map.put("token",jwt);

        redisCache.setCacheObject("login:"+id,principal);

        return new ResponseResult<>(200,"success",map);
    }

    @Override
    public ResponseResult logout() {

        //获取SecurityContextHolder中得id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();

        User user = principal.getUser();
        Long id = user.getId();
        boolean b = redisCache.deleteObject("login:" + id);
        if (b) {
            return new  ResponseResult(200,"success");
        }
        return new ResponseResult(200,"error");
    }
}
