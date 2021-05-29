package me.jun.guestbook.ui;

import me.jun.guestbook.post.application.exception.GuestMisMatchException;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.ui.error.ErrorCode;
import me.jun.guestbook.ui.error.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindExceptionHandler(BindException e) {
        Logger logger = LoggerFactory.getLogger("empty input error logger");
        logger.info("empty input");
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.EMPTY_VALUE);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> postNotFoundExceptionHandler(PostNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.POST_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(GuestMisMatchException.class)
    public ResponseEntity<ErrorResponse> postNotFoundExceptionHandler(GuestMisMatchException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.GUEST_MISMATCH);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }
}
