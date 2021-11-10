package me.jun.common;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.exception.CommentNotFoundException;
import me.jun.common.error.ErrorCode;
import me.jun.common.error.ErrorResponse;
import me.jun.guest.application.exception.DuplicatedEmailException;
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

    private final ErrorEntityModelCreator errorEntityModelCreator;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EntityModel<ErrorResponse>>
    methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.EMPTY_VALUE);
        return new ResponseEntity<>(errorEntityModelCreator.createErrorEntityModel(errorResponse),
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<EntityModel<ErrorResponse>>
    postNotFoundExceptionHandler(PostNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.POST_NOT_FOUND);
        return new ResponseEntity<>(errorEntityModelCreator.createErrorEntityModel(errorResponse),
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(PostWriterMismatchException.class)
    public ResponseEntity<EntityModel<ErrorResponse>>
    writerMismatchExceptionHandler(PostWriterMismatchException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.GUEST_MISMATCH);
        return new ResponseEntity<>(errorEntityModelCreator.createErrorEntityModel(errorResponse),
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<EntityModel<ErrorResponse>>
    NoTokenExceptionHandler(InvalidTokenException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.UNAUTHORIZED);
        return new ResponseEntity<>(errorEntityModelCreator.createErrorEntityModel(errorResponse),
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<EntityModel<ErrorResponse>>
    DuplicatedEmailExceptionHandler(DuplicatedEmailException e) {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.GUEST_ALREADY_EXIST);
        return new ResponseEntity<>(errorEntityModelCreator.createErrorEntityModel(errorResponse),
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<EntityModel<ErrorResponse>>
    commentNotFoundExceptionHandler() {
        ErrorResponse errorResponse = ErrorResponse.from(ErrorCode.COMMENT_NOT_FOUND);
        return new ResponseEntity<>(errorEntityModelCreator.createErrorEntityModel(errorResponse),
                HttpStatus.valueOf(errorResponse.getStatusCode()));
    }
}
