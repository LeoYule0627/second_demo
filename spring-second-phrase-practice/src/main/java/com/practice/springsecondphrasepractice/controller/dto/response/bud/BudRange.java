package com.practice.springsecondphrasepractice.controller.dto.response.bud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudRange {
    private String budYmd;
    private String budPrevYmd;
    private String budNextYmd;
}
