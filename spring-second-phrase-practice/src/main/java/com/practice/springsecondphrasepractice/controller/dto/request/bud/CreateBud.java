package com.practice.springsecondphrasepractice.controller.dto.request.bud;

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
public class CreateBud {
    @NotEmpty
    @Pattern(regexp = "^[?=\\d]{8}",message = "格式錯誤")
    private String budYmd;
    @NotEmpty
    @Pattern(regexp = "^[?=N|Y]",message = "格式錯誤")
    private String budType;
}
