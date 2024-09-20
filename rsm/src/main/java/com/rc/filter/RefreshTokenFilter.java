package com.rc.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.rc.domain.dto.LoginUser;
import com.rc.domain.dto.UserDTO;
import com.rc.utils.UserHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static com.rc.utils.RedisConstants.LOGIN_USER_KEY;
import static com.rc.utils.RedisConstants.LOGIN_USER_TTL;

@AllArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            // 放行，无token的情况
            filterChain.doFilter(request, response);
            return;
        }

        String key = LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        if (userMap.isEmpty()) {
            // 放行，无效token
            filterChain.doFilter(request, response);
            return;
        }

        //解析token
        JWT jwt = JWTUtil.parseToken(token);
        try {
            // 验证token是否过期
            JWTValidator.of(jwt).validateDate();
        } catch (Exception e) {
            // 放行，无效token
            filterChain.doFilter(request, response);
            return;
        }

        // 将哈希数据转换成 LoginUser 对象
        UserDTO userDTO = UserDTO.parse((String) userMap.get("user"));

        LoginUser loginUser = new LoginUser();
        loginUser.setPermissions(LoginUser.parsePermissions((String) userMap.get("permissions")));
        loginUser.setUser(userDTO);
        loginUser.setAuthorities(LoginUser.convertToAuthorities(LoginUser.parsePermissions((String) userMap.get("authorities"))));
        UserHolder.saveUser(userDTO);
        // 刷新token有效期
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
        System.out.println("Auth:"+loginUser.getAuthorities());
        // 设置用户信息到 SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
