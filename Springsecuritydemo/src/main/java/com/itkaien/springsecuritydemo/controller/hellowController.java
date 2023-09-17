package com.itkaien.springsecuritydemo.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hellowController {
    @GetMapping("/hellow")
    @ApiOperation("测试demo")
    @PreAuthorize("hasAnyAuthority('system:dept:list')")
    public String gethellow(){
        return "hellow";
    }


}
