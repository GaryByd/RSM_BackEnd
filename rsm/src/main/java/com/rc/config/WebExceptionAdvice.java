package com.rc.config;


import com.rc.domain.dto.Result;
import com.rc.handler.AccessDeniedHandlerImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        if (e instanceof AccessDeniedException) {
            return Result.fail(403,"权限不足");
        }
        if(e instanceof AuthenticationException){
            return null;
        }
        log.error(e.toString(), e);
        return Result.fail("服务器异常");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public void handleNotFound(HttpServletResponse response) throws IOException {
        // 返回 404 错误
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "请求的路径不存在");
    }
}
