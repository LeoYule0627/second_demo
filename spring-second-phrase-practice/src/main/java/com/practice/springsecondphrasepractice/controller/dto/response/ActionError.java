package com.practice.springsecondphrasepractice.controller.dto.response;


import lombok.Getter;

@Getter
public enum ActionError {
    Query("查詢失敗"),
    CREATE("新增失敗"),
    Modify("異動失敗"),
    DELETE("刪除失敗"),
    VALIDATE("檢核失敗"),
    SYSTEM("系統錯誤");

    private final String msg;

    ActionError(String msg) {
        this.msg = msg;
    }


}
