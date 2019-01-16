package com.example.exception;

import org.springframework.security.core.AuthenticationException;

public class MyAuthException extends AuthenticationException {
    public MyAuthException(String msg) {
        super(msg);
    }
}
