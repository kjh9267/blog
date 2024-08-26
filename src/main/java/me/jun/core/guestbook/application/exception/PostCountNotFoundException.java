package me.jun.core.guestbook.application.exception;

import lombok.Getter;
import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.POST_COUNT_NOT_FOUND;

@Getter
public class PostCountNotFoundException extends BusinessException {

    public PostCountNotFoundException(Long id) {
        super(POST_COUNT_NOT_FOUND, POST_COUNT_NOT_FOUND.errorMessageOf(id));
    }
}
