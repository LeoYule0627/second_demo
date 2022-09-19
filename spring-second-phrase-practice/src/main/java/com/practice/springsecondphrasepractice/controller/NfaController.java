package com.practice.springsecondphrasepractice.controller;

import com.practice.springsecondphrasepractice.controller.dto.request.Nfa.CreateNfa;
import com.practice.springsecondphrasepractice.controller.dto.request.Nfa.DeleteNfa;
import com.practice.springsecondphrasepractice.controller.dto.request.Nfa.UpdateNfa;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.service.NfaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/nfa")
@Validated
public class NfaController {
    @Autowired
    NfaService nfaService;

    @GetMapping()
    public List<LinkedHashMap> getAllNfa(
            @RequestParam(required = false)
            String subject,
            @RequestParam(required = false)
            @Pattern(regexp = "^[(?=\\d)]{8}", message = "起始日期 格式錯誤")
            String startDate,
            @RequestParam(required = false)
            @Pattern(regexp = "^[(?=\\d)]{8}", message = "結束日期 格式錯誤")
            String endDate
    ) throws Exception {
        try {
            List<LinkedHashMap> response = new ArrayList<>();
            List<String> message = new ArrayList<>();
            if (subject == null && startDate == null && endDate == null) {
                response = this.nfaService.getAllNfa();
            } else {
                response = this.nfaService.getNfaRange(subject, startDate, endDate);
            }
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }


    @PostMapping()
    public Map createNfa(@RequestBody @Valid CreateNfa createNfa) throws Exception {
        try {
            if (Integer.parseInt(createNfa.getStartDate()) > Integer.parseInt(createNfa.getEndDate())) {
                List<String> message = new ArrayList<>();
                message.add("起始日期 不能大於 結束日期");
                throw new ParamInvalidException(message);
            }
            Map response = this.nfaService.createNfa(createNfa);
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

    @PutMapping("/{nfaUuid}")
    public Map updateNfa(
            @PathVariable
            @Pattern(regexp = "^[?=\\d]{17}", message = "nfaUuid 格式錯誤")
            String nfaUuid,
            @RequestBody @Valid UpdateNfa updateNfa) throws Exception {
        try {
            if (Integer.parseInt(updateNfa.getStartDate()) > Integer.parseInt(updateNfa.getEndDate())) {
                List<String> message = new ArrayList<>();
                message.add("起始日期 不能大於 結束日期");
                throw new ParamInvalidException(message);
            }
            Map response = this.nfaService.updateNfa(nfaUuid, updateNfa);
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

    @PostMapping("/{nfaUuid}")
    public Map deleteNfa(
            @PathVariable
            @Pattern(regexp = "^[?=\\d]{17}", message = "nfaUuid 格式錯誤")
            String nfaUuid,
            @RequestBody @Valid DeleteNfa deleteNfa) throws Exception {
        try {
            Map response = this.nfaService.deleteNfa(nfaUuid, deleteNfa);
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }

    }


}
