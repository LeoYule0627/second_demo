package com.practice.springsecondphrasepractice.controller.dto.request.prod;

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
public class UpdateProd {
    @NotEmpty
    private String prodName;
    @NotEmpty
    private String prodEname;
    @NotEmpty
    @Pattern(regexp = "[?=Y|N]",message = "格式錯誤")
    private String prodEnable;
}
