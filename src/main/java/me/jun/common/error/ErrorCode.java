package me.jun.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    INVALID_INPUT(BAD_REQUEST, "Check your request \n {}"),

    POST_NOT_FOUND(NOT_FOUND, "No post with Id {}."),

    POST_COUNT_NOT_FOUND(NOT_FOUND, "No post count with postId {}."),

    MEMBER_MISMATCH(FORBIDDEN, "You are not authorized to post {}."),

    INVALID_PASSWORD(UNAUTHORIZED, "Invalid password"),

    INVALID_TOKEN(UNAUTHORIZED, "Invalid Token"),

    MEMBER_NOT_FOUND(NOT_FOUND, "Email {} is not exist"),

    MEMBER_ALREADY_EXIST(CONFLICT, "Email {} is already exist"),

    COMMENT_NOT_FOUND(NOT_FOUND, "No comment with Id {}."),

    ARTICLE_NOT_FOUND(NOT_FOUND, "No article with Id {}."),

    CATEGORY_NOT_FOUND(NOT_FOUND, "No category with Id {}");

    private final int statusCode;

    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String message) {
        this.statusCode = httpStatus.value();
        this.message = message;
    }

    public String errorMessageOf(Object param) {
        requireNonNull(param);
        return String.format(message, param);
    }
}
