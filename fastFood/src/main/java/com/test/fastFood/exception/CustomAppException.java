package com.test.fastFood.exception;


import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomAppException extends RuntimeException {
    private final UUID id;
    private final String message;

    public CustomAppException(String message) {
        super(message);
        this.id = UUID.randomUUID();
        this.message = message;
    }

}
