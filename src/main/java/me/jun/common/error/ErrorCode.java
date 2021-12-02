package me.jun.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    EMPTY_VALUE(400, "Invalid input"),
    POST_NOT_FOUND(404, "No post"),
    MEMBER_MISMATCH(403, "Member Mismatch"),
    UNAUTHORIZED(401, "Invalid token"),
    MEMBER_ALREADY_EXIST(409, "Email already exist"),
    COMMENT_NOT_FOUND(404, "No comment");

    private final int statusCode;

    private final String message;

    ErrorCode(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
