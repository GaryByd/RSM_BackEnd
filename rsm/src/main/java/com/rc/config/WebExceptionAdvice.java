package com.rc.config;


import com.rc.domain.dto.Result;
import com.rc.handler.AccessDeniedHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.error(e.toString(), e);
        if (e instanceof AccessDeniedException) {
            return Result.fail(403,"权限不足");
        }
        return Result.fail("服务器异常");
    }
}
