package me.jun.guestbook.ui.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    EMPTY_VALUE(400, "Empty input"),
    POST_NOT_FOUND(404, "No post");

    private final int statusCode;
    private final String message;

    ErrorCode(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
