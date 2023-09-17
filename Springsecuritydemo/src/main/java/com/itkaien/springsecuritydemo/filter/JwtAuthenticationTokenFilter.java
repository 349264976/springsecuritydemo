package com.itkaien.springsecuritydemo.filter;

import com.itkaien.springsecuritydemo.domain.LoginUser;
import com.itkaien.springsecuritydemo.utils.JwtUtil;
import com.itkaien.springsecuritydemo.utils.RedisCache;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.rmi.RemoteException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
            //从redis中获取用户信息
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("token非法");
        }
        String key = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(key);
        if (loginUser == null) {
            throw new RemoteException("用户未登录");
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken
                        (loginUser,null,
                                loginUser.getAuthorities()));

        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
