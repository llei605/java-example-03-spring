package com.example.config;

import com.example.filter.MyAuthenticationFilter;
import com.example.filter.MyLoginFilter;
import com.example.service.impl.AuthenticationProviderImpl;
import com.example.service.impl.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * MySecurityConfig
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    //我理解这里应该是用户密码的加密方式，本程序采用的是MD5
    //    @Autowired
    //    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers("/", "/login").permitAll()  // 表示这两个路径不需要权限
            .anyRequest().authenticated()
            .and().addFilter(new MyLoginFilter(authenticationManager())) // 验证用户的登陆
            .addFilter(new MyAuthenticationFilter(authenticationManager())); // 验证用户登录后携带的tokent
    }

    // 身份验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这里自定义用户验证的方式
//        auth.authenticationProvider(new AuthenticationProviderImpl(userDetailsService, bCryptPasswordEncoder));
        auth.authenticationProvider(new AuthenticationProviderImpl(userDetailsService, null));
    }
}