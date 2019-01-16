package com.example.service.impl;

import org.springframework.security.core.GrantedAuthority;

/**
 * 作用：将数据库中的Authority转成Security规定的Authority ??
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
