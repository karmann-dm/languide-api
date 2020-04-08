package com.karmanno.languagelearningappapi.dto;

import lombok.Data;

@Data
public class ApiResponse {
    private Boolean success;
    private String errorMessage;
    private Object payload;

    public static ApiResponse success(Object payload) {
        return new ApiResponse()
                .setSuccess(true)
                .setPayload(payload);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse()
                .setSuccess(false)
                .setErrorMessage(message);
    }
}
