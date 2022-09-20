package com.practice.springsecondphrasepractice.controller;

import com.practice.springsecondphrasepractice.controller.dto.request.Prod.CreateProd;
import com.practice.springsecondphrasepractice.controller.dto.request.Prod.DeleteProd;
import com.practice.springsecondphrasepractice.controller.dto.request.Prod.UpdateProd;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/prod")
@Validated
public class ProdController {

    @Autowired
    ProdService prodService;

    @GetMapping()
    public List<LinkedHashMap> getProd(
            @RequestParam(required = false)
            @Pattern(regexp = "[?=[A-Z]]{3}", message = "類別 格式錯誤")
            String kind,
            @RequestParam(required = false)
            @Pattern(regexp = "[?=[A-Z]]{3}", message = "類別 格式錯誤")
            String ccy
    ) throws Exception {
        try {
            List<LinkedHashMap> response = new ArrayList<>();
            List<String> message = new ArrayList<>();
            List<String> kindList = new ArrayList<>();
            kindList.add("EAT");
            kindList.add("USE");
            List<String> ccyList = new ArrayList<>();
            ccyList.add("JPN");
            ccyList.add("TWD");
            ccyList.add("THB");
            ccyList.add("USD");
            if (kind == null && ccy == null) {
                response = this.prodService.getAllProd();
                return response;
            } else if (kind != null && ccy != null) {
                message.add("不可同時存在類別、幣別");
                throw new ParamInvalidException(message);
            } else if (kind != null && kindList.stream().noneMatch(s -> s.equals(kind))) {
                message.add("類別 不存在");
                throw new ParamInvalidException(message);
            } else if (ccy != null && ccyList.stream().noneMatch(s -> s.equals(ccy))) {
                message.add("幣別 不存在");
                throw new ParamInvalidException(message);
            } else {
                if(kind != null) response = this.prodService.getKind(kind);
                if(ccy != null) response = this.prodService.getCcy(ccy);
            }
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

    @GetMapping("/{prodId}")
    public LinkedHashMap getOneProd(
            @PathVariable
            @NotEmpty
            @Pattern(regexp = "^[?=[A-Z]]{3}+_+[?=[A-Z]]{3}", message = "prodId 格式錯誤")
            String prodId) throws Exception{
        try{
            LinkedHashMap response = this.prodService.getOneProd(prodId);
            return response;
        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    @PostMapping()
    public Map createProd(@RequestBody @Valid CreateProd createProd) throws Exception{
        try{
            Map response = this.prodService.createProd(createProd);
            return response;
        }catch (Exception e){
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

    @PutMapping("/{prodId}")
    public Map updateProdEnable(
            @PathVariable
            @NotEmpty
            @Pattern(regexp = "^[?=[A-Z]]{3}+_+[?=[A-Z]]{3}", message = "prodId 格式錯誤")
            String prodId,
            @RequestBody @Valid UpdateProd updateProd) throws Exception{
        try{
            Map response = this.prodService.updateProdEnable(prodId, updateProd);
            return response;
        }catch (Exception e){
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

    @PostMapping("/{prodId}")
    public Map deleteProdEnable(
            @PathVariable
            @NotEmpty
            @Pattern(regexp = "^[?=[A-Z]]{3}+_+[?=[A-Z]]{3}", message = "prodId 格式錯誤")
            String prodId,
            @RequestBody  @Valid DeleteProd deleteProd) throws Exception {
        try {
            Map response = this.prodService.deleteProdEnable(prodId, deleteProd);
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

}
