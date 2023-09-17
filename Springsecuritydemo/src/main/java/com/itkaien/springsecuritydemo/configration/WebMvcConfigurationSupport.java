package com.itkaien.springsecuritydemo.configration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebMvcConfigurationSupport extends org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport {

    /*
     * 配置静态资源，避免静态资源请求被拦截
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        super.addResourceHandlers(registry);

    }
}
