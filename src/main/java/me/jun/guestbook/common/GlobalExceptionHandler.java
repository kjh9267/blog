package me.jun.guestbook.common;

import me.jun.guestbook.post.application.exception.WriterMismatchException;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.security.InvalidTokenException;
import me.jun.guestbook.common.error.ErrorCode;
import me.jun.guestbook.common.error.ErrorResponse;
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

    @ExceptionHandler(WriterMismatchException.class)
    public ResponseEntity<ErrorResponse> writerMismatchExceptionHandler(WriterMismatchException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.GUEST_MISMATCH);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> NoTokenExceptionHandler(InvalidTokenException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatusCode()));
    }
}
