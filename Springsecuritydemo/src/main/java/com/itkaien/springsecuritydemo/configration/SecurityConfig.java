package com.itkaien.springsecuritydemo.configration;


import com.itkaien.springsecuritydemo.filter.JwtAuthenticationTokenFilter;
import com.itkaien.springsecuritydemo.handler.AccessDeniedHandlerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Test
    public void test(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("1");
        String encode1 = bCryptPasswordEncoder.encode("1");

        System.out.println(encode);
        System.out.println(encode1);

        boolean matches = bCryptPasswordEncoder.matches("1234", "$2a$10$4HrcgJa3RWjYKSu686XEl.mWfj2pt5DFmgHNmLZjbkVmEExqs09xe");
        System.out.println(matches);
    }
    //创建BcrypPasswordEncoder注入容器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean(name = "Authentication")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过session获取SecurityCobtext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //匹配允许匿名访问的的路径
                .antMatchers("/user/login",
                        "/v2/api-docs",
                 "/v3/api-docs",
                "/swagger-resources/**",
                "/swagger-ui/**",
                        "/**/doc.html",
                "/webjars/**")

                .anonymous()
                //除了上面的所有请求都需要鉴权认证
                .anyRequest()
                .authenticated();
        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);
        //配置异常处理器
        //认证异常返回信息处理器
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        //授权失败返回信息处理器
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        //允许跨域
        http.cors();
    }
}
