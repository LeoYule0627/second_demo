package com.practice.springsecondphrasepractice.controller;

import com.practice.springsecondphrasepractice.controller.dto.request.Date.CreateDate;
import com.practice.springsecondphrasepractice.controller.dto.request.Date.UpdateDate;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.Bud;
import com.practice.springsecondphrasepractice.service.BudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bud")
@Validated
public class BudController {

    @Autowired
    BudService budService;

    @GetMapping()
    public List<Bud> getDate(
            @RequestParam(required = false)
            @Pattern(regexp = "^[(?=\\d)]{8}", message = "起始日期 格式錯誤")
            String startDate,
            @RequestParam(required = false)
            @Pattern(regexp = "^[(?=\\d)]{8}", message = "結束日期 格式錯誤")
            String endDate,
            @RequestParam(required = false)
            @Pattern(regexp = "^[?=\\d]{4}", message = "年度 格式錯誤")
            String year
    ) throws Exception {
        try {
            List<Bud> response;
            List<String> message = new ArrayList<>();
            if (year == null) {
                if (startDate == null && endDate == null) {
                    response = this.budService.getAllDate();
                } else if (startDate == null || endDate == null) {
                    message.add("需有 起始日期、結束日期");
                    throw new ParamInvalidException(message);
                } else if (Integer.parseInt(startDate) > Integer.parseInt(endDate)) {
                    message.add("起始日期 不能大於 結束日期");
                    throw new ParamInvalidException(message);
                } else {
                    response = this.budService.getDateRange(startDate, endDate);
                }
            } else {
                if (startDate != null || endDate != null) {
                    message.add("不可同時有 年度 和 起始日期或結束日期");
                    throw new ParamInvalidException(message);
                }
                response = this.budService.getYearDate(year);
            }
            return response;
        } catch (Exception e){
            System.out.println(e);
            if(e instanceof DataNotFoundException){
                throw e;
            }
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

    @GetMapping("/{budYmd}")
    public Bud getOneDate(
            @PathVariable
            @NotEmpty
            @Pattern(regexp = "^[(?=\\d)]{8}", message = "日期 格式錯誤")
            String budYmd
    ) throws Exception{
        try{
            Bud response = this.budService.getOneDate(budYmd);
            return response;
        }catch (Exception e){
            if (e instanceof MissingServletRequestParameterException) {
                throw e;
            }
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    @GetMapping("/business/{budYmd}")
    public Map getBeforeAndAfter(
            @PathVariable
            @NotEmpty
            @Pattern(regexp = "^[(?=\\d)]{8}", message = "日期 格式錯誤")
            String budYmd
    ) throws Exception {
        try{
            Map response = this.budService.getBeforeAndAfter(budYmd);
            return response;
        }catch (Exception e){
            if (e instanceof MissingServletRequestParameterException) {
                throw e;
            }
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }


    @PostMapping()
    public Map createDate(@RequestBody @Valid CreateDate createDate) throws Exception {
        try{
            Map response = this.budService.createDate(createDate);
            return response;
        }catch (Exception e){
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

    @PutMapping("/{budYmd}")
    public Map deleteDateType(
            @PathVariable
            @NotEmpty
            @Pattern(regexp = "^[?=\\d]{8}", message = "日期 格式錯誤")
            String budYmd,
            @RequestBody @Valid UpdateDate updateDate) throws Exception {
        try{
            Map response = this.budService.updateDateType(budYmd, updateDate);
            return response;
        }catch (Exception e){
            if (e instanceof MissingServletRequestParameterException) {
                throw e;
            }
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }
}
