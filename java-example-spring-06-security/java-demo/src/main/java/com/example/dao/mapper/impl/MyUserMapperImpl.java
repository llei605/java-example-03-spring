package com.example.dao.mapper.impl;

import com.example.dao.mapper.MyUserMapper;
import com.example.po.UserPO;
import org.springframework.stereotype.Service;

/**
 * 模拟操作：模拟数据库查询用户
 */
@Service
public class MyUserMapperImpl implements MyUserMapper {
    @Override
    public UserPO findUserByName(String username) {

        UserPO userPO = new UserPO();
        userPO.setId((long)1);
        userPO.setName("aaa");
        userPO.setPassword("111");
        userPO.setEnabled(true);
        userPO.setEmail("qq@qq.com");

        return userPO;
    }
}
