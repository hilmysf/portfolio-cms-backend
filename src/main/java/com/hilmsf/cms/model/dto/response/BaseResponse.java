package com.hilmsf.cms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BaseResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return BaseResponse.<T>builder()
                .status(200)
                .message("Success")
                .data(data)
                .build();
    }

    public static BaseResponse<?> error(int status, String message) {
        return BaseResponse.builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }
}
