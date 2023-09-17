package com.itkaien.springsecuritydemo.handler;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.itkaien.springsecuritydemo.domain.ResponseResult;
import com.itkaien.springsecuritydemo.utils.WebUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseResult responseResult = new ResponseResult(HttpStatus.HTTP_FORBIDDEN, "授权失败");
        String jsonString = JSON.toJSONString(responseResult);
        //处理异常
        WebUtils.renderString(httpServletResponse,jsonString);
    }
}
