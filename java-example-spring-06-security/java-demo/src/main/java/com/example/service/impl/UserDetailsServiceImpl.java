package com.example.service.impl;

import com.example.dao.mapper.MyUserMapper;
import com.example.exception.MyAuthException;
import com.example.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 作用：在数据库中查找用户，并按照Security的要求返回用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MyUserMapper myUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 在数据库查询用户
        UserPO userPO = myUserMapper.findUserByName(s);
        if (userPO == null) {
            new MyAuthException("User not find.");
        }
        User user = new User(userPO.getName(), userPO.getPassword(), Collections.emptyList());
        return user;
    }
}
