package com.test.fastFood.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException{
    @ExceptionHandler
    public ResponseEntity<?> handleException(ResourceNotFoundException e){
        CustomAppException customAppException = new CustomAppException(e.getMessage());
        log.error("ID " + customAppException.getId().toString(), e);
        return new ResponseEntity<>(("ERROR: " + customAppException.getMessage() + "\nid: " + customAppException.getId()),
                HttpStatus.NOT_FOUND);
    }
}

