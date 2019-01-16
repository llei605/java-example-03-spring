package com.example.dao.mapper;

import com.example.po.UserPO;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserMapper {
    public UserPO findUserByName(String username);
}
