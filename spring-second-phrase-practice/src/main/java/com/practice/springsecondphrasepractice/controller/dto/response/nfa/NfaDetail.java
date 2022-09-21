package com.practice.springsecondphrasepractice.controller.dto.response.nfa;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NfaDetail {

    private String uuid;
    private String subject;
    private String content;
    private String enable;
    private LocalDateTime createTime;
}
