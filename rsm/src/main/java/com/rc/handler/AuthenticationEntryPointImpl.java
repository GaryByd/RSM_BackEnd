package com.rc.handler;

import com.alibaba.fastjson.JSON;

import com.rc.domain.dto.Result;
import com.rc.utils.WebUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 设置响应内容类型
        response.setContentType("application/json;charset=UTF-8");

        // 构造结果对象
        Result result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");

        // 将结果对象转换为 JSON 字符串
        String json = JSON.toJSONString(result);

        // 输出 JSON 响应
        WebUtils.renderString(response, json);

        // 记录错误日志
        log.error("Authentication failed: {}", authException.getMessage());
    }

}
