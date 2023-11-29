package com.test.fastFood.utils;

import com.test.fastFood.entity.UserEntity;
import com.test.fastFood.exception.UserNotFoundException;
import com.test.fastFood.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {
    public static Long getCurrentUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUserId();
        }
        throw new UserNotFoundException("User not found");
    }
}
