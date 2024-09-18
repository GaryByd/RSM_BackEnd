package com.rc.exception;

public class EmptyUserStatusException extends RuntimeException{
    public EmptyUserStatusException() {
        super("用户状态为空");
    }

    public EmptyUserStatusException(String message) {
        super(message);
    }
}
