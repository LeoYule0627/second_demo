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
public class UpdateBud {
    @NotEmpty
    @Pattern(regexp = "^[?=N|Y]",message = "格式錯誤")
    private String budType;
}
