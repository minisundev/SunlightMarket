package com.raincloud.sunlightmarket.global.exception;

import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.RejectedExecutionException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }
    @ExceptionHandler({NullPointerException.class})
    public ApiResponse<?> handleNullPointerException(NullPointerException ex){
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

    @ExceptionHandler({RejectedExecutionException.class})
    public ApiResponse<?> handleRejectedExecutionException(RejectedExecutionException ex){
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ApiResponse<?> handleEntityNotFoundException(EntityNotFoundException ex){
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
    }

}
