package com.example.service.impl;

import com.example.exception.MyAuthException;
import com.example.service.impl.GrantedAuthorityImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

public class AuthenticationProviderImpl implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationProviderImpl(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 作用：验证用户的登陆信息
     *       > 如果登陆成功，设置Authntication （即授权）
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 通过用户名在数据库中查询该用户
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 核对密码
        String dbPassword = "111"; // 从数据库获得密码
//        String encoderPassword = DigestUtils.md5DigestAsHex(password.getBytes()); // 对用户输入的密码进行MD5加密
        String encoderPassword = password;

        if (!dbPassword.equals(encoderPassword)) {
            new MyAuthException("Password Incorrect!");
        }

        // 当用户名和密码正确后，从数据库获取用户权限，并返回用户权限
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthorityImpl("ADMIN"));
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
