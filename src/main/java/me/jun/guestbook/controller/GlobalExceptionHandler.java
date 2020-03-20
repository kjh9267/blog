package me.jun.guestbook.controller;

import me.jun.guestbook.error.ErrorCode;
import me.jun.guestbook.error.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> bindExceptionHandler(BindException e) {
        Logger logger = LoggerFactory.getLogger("empty input error logger");
        logger.info("empty input");
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.EMPTY_VALUE);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
