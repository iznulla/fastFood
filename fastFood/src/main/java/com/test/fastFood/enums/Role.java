package com.test.fastFood.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER, WAITER;

    @Override
    public String getAuthority() {
        return name();
    }
}
