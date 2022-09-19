package com.practice.springsecondphrasepractice.controller.dto.request.Nfa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNfa {
    @NotEmpty
    private String subject;
    @NotEmpty
    private String content;
    @NotEmpty
    @Pattern(regexp = "^[?=N|Y]",message = "格式錯誤")
    private String enable;
    @NotEmpty
    @Pattern(regexp = "^[?=\\d]{8}",message = "格式錯誤")
    private String startDate;
    @NotEmpty
    @Pattern(regexp = "^[?=\\d]{8}",message = "格式錯誤")
    private String endDate;
}