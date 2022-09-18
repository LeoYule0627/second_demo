package com.practice.springsecondphrasepractice.controller.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String error;

    private String message;

    private List<FieldError> fieldErrors = new ArrayList<>();

    private List<CheckError> checkErrors = new ArrayList<>();


    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class FieldError {
        private final String field;
        private final String message;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class CheckError {
        private final String message;
    }

    public void addFieldError(String field, String message) {
        fieldErrors.add(new FieldError(field, message));
    }

    public void addCheckError(String message) {
        checkErrors.add(new CheckError(message));
    }
}
