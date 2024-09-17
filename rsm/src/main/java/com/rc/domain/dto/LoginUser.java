package com.rc.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.rc.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data

@NoArgsConstructor
public class LoginUser implements UserDetails {

    private UserDTO user;//封装用户信息

    private List<String> permissions;//存储权限信息

    public LoginUser(UserDTO user, List<String> list) {
        this.user = user;
        this.permissions = list;
    }
    //获取权限
    @JSONField(serialize = false) //忽略
    private List<SimpleGrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null){
            return authorities;
        }
        ////把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities
        authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
    //账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
    public static List<String> parsePermissions(String str) {
        // 去掉方括号并去除空格
        str = str.replace("[", "").replace("]", "").trim();

        // 如果字符串为空，则返回空列表
        if (str.isEmpty()) {
            return List.of();
        }

        // 将字符串按逗号分隔，并返回 List<String>
        return Arrays.stream(str.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static List<SimpleGrantedAuthority> convertToAuthorities(List<String> permissions) {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)  // 将每个权限字符串转换为 SimpleGrantedAuthority
                .collect(Collectors.toList());     // 收集成一个列表
    }


}
