package me.jun.guestbook.ui.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    EMPTY_VALUE(400, "Empty input");

    private final int statusCode;
    private final String message;

    ErrorCode(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
