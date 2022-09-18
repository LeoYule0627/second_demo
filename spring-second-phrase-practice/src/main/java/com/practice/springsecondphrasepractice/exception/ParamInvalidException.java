package com.practice.springsecondphrasepractice.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ParamInvalidException extends Exception {

    private List<String> errMessages;
    private String message;

    public ParamInvalidException(List<String> errMessages) {
        this.message = "";
        this.errMessages = errMessages;
    }
}
