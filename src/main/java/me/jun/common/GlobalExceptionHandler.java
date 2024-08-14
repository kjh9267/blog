package me.jun.common;

import lombok.RequiredArgsConstructor;
import me.jun.common.error.ErrorResponse;
import me.jun.support.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException exception) {
        ErrorResponse errorResponse = ErrorResponse.from(exception.getErrorCode());

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode())
        );
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.from(exception, BAD_REQUEST.value());

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.from(exception, INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode())
        );
    }
}