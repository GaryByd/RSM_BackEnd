package com.rc.config;

import com.rc.domain.dto.LoginUser;
import com.rc.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取传入的 openId
        String openId = authentication.getPrincipal().toString();

        // 根据 openId 加载用户信息
        LoginUser loginUser = (LoginUser) userDetailsService.loadUserByUsername(openId);

        if (loginUser == null) {
            throw new RuntimeException("未找到用户");
        }
        // 返回通过认证的 Authentication 对象
        return new UsernamePasswordAuthenticationToken(
                loginUser, null, loginUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 支持 UsernamePasswordAuthenticationToken 类型的认证
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
