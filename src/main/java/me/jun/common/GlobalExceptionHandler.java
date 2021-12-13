package me.jun.common;

import lombok.RequiredArgsConstructor;
import me.jun.common.error.ErrorCode;
import me.jun.common.error.ErrorResponse;
import me.jun.common.security.InvalidTokenException;
import me.jun.guestbook.application.exception.CommentNotFoundException;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.exception.PostWriterMismatchException;
import me.jun.member.application.exception.DuplicatedEmailException;
import me.jun.support.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException exception) {
        ErrorResponse errorResponse = ErrorResponse.from(exception, exception.getErrorCode());

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = ErrorResponse.from(exception, BAD_REQUEST.value());

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode())
        );
    }
}