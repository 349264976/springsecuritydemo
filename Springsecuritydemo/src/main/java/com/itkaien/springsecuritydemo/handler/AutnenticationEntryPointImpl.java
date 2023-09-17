package com.itkaien.springsecuritydemo.handler;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.itkaien.springsecuritydemo.domain.ResponseResult;
import com.itkaien.springsecuritydemo.utils.WebUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AutnenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //处理异常
        ResponseResult responseResult=new ResponseResult(HttpStatus.HTTP_UNAUTHORIZED,"用户名认证失败");
        String jsonString = JSON.toJSONString(responseResult);
        WebUtils.renderString(httpServletResponse,jsonString);
    }
}
