package com.example.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 作用：验证用户登陆后携带的tocket
 */
public class MyAuthenticationFilter extends BasicAuthenticationFilter {
    public MyAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public MyAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }
}
