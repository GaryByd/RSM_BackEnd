package com.rc.config;


import com.rc.domain.dto.Result;
import com.rc.exception.EmptyUserStatusException;
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
        if(e instanceof EmptyUserStatusException){
            return Result.fail(520,"未绑定账号");
        }
        //找不到路径
        log.error(e.toString(), e);
        return Result.fail("服务器异常");
    }
}
