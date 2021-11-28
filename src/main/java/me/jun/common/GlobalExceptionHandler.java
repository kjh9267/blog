package me.jun.common;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.exception.CommentNotFoundException;
import me.jun.common.error.ErrorCode;
import me.jun.common.error.ErrorResponse;
import me.jun.member.application.exception.DuplicatedEmailException;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.exception.PostWriterMismatchException;
import me.jun.common.security.InvalidTokenException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.EMPTY_VALUE);
        return new ResponseEntity<>(errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> postNotFoundExceptionHandler(PostNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.POST_NOT_FOUND);
        return new ResponseEntity<>(errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(PostWriterMismatchException.class)
    public ResponseEntity<ErrorResponse> writerMismatchExceptionHandler(PostWriterMismatchException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.MEMBER_MISMATCH);
        return new ResponseEntity<>(errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> NoTokenExceptionHandler(InvalidTokenException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<ErrorResponse> DuplicatedEmailExceptionHandler(DuplicatedEmailException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.MEMBER_ALREADY_EXIST);
        return new ResponseEntity<>(errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> commentNotFoundExceptionHandler() {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.COMMENT_NOT_FOUND);
        return new ResponseEntity<>(errorResponse,
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }
}
