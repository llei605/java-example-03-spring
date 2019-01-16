package com.example.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * MyLoginFilter
 */
public class MyLoginFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public MyLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // step 1. 获取request中的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if  (username == null) { username = ""; };
        if  (password == null) { password = ""; };

        // step 2. 验证用户名和密码
        ArrayList<? extends GrantedAuthority> authorities = new ArrayList<>();
        UsernamePasswordAuthenticationToken authenticationToken = 
        new UsernamePasswordAuthenticationToken(username, password, authorities);

        return authenticationToken;
    }

    /**
    * 作用：当用户登录成功后，Filter会调用此方法，因此可以在这里生成并返回token
    */
    @Override
    protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        
        long EXPIREATION_TIME = 5 * 60 * 60 * 1000; // 过期时间：5分钟
        String SECRET = "P@ssw0rd";                 // JWT密码
        String TOKEN_PREFIX = "Bearer ";             // Token前缀
        String HEADER_STRING = "Authorization123";     // 存放Token的Header Key
    
        /**
         * JWT 的生成token的方法
         */
        String retToken = Jwts.builder()
                .claim("authorities", "ROLE_ADMIN,AUTH_WRITE")
                .setSubject(authResult.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIREATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET) // 加密方式
                .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + retToken);
    }
}