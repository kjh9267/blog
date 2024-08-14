package me.jun.common.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private int statusCode;

    private String message;

    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .statusCode(errorCode.getStatusCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse from(Throwable throwable, int statusCode) {
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .message(throwable.getMessage())
                .build();
    }
}
