package me.jun.guestbook.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    EMPTY_VALUE(400, "Invalid input"),
    POST_NOT_FOUND(404, "No post"),
    GUEST_MISMATCH(403, "Guest Mismatch"),
    UNAUTHORIZED(401, "Invalid token"),
    GUEST_ALREADY_EXIST(409, "Email already exist");

    private final int statusCode;
    private final String message;

    ErrorCode(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
