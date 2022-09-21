package com.practice.springsecondphrasepractice.controller.dto.request.prod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProd {
    @NotEmpty
    @Pattern(regexp = "[?=[A-Z]]{3}" ,message = "格式錯誤")
    private String prodKind;
    @NotEmpty
    @Pattern(regexp = "[?=[A-Z]]{3}",message = "格式錯誤")
    private String prodCcy;
    @NotEmpty
    private String prodName;
    @NotEmpty
    private String prodEname;
    @NotEmpty
    @Pattern(regexp = "[?=Y|N]",message = "格式錯誤")
    private String prodEnable;
}