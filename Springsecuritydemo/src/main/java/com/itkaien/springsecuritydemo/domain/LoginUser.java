package com.itkaien.springsecuritydemo.domain;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Data
@NoArgsConstructor
@ApiModel("用户实体类")
public class LoginUser implements UserDetails {
    @ApiModelProperty("用户实体")
    private User user;

    @ApiModelProperty("用户角色权限")
    private List<String> permissions;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }
    @JSONField(serialize = false)
    private List< GrantedAuthority> authorities ;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //把permission中String类型的权限信息封装成SimpleGrantedAuthority
//        List<SimpleGrantedAuthority> collect = permissions.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//        collect.stream().forEach(authorities::add);
//        permissions.stream().forEach(permission -> authorities
//                .add(new SimpleGrantedAuthority(permission)));
        if (authorities!=null&&!authorities.isEmpty()){
            return authorities;
        }
       authorities=permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
