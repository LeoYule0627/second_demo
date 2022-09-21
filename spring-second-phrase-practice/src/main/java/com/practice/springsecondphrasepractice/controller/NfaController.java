package com.practice.springsecondphrasepractice.controller;

import com.practice.springsecondphrasepractice.controller.dto.request.nfa.CreateNfa;
import com.practice.springsecondphrasepractice.controller.dto.request.nfa.DeleteNfa;
import com.practice.springsecondphrasepractice.controller.dto.request.nfa.UpdateNfa;
import com.practice.springsecondphrasepractice.controller.dto.response.nfa.NfaDetail;
import com.practice.springsecondphrasepractice.controller.dto.response.nfa.NfaStatus;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.service.NfaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/nfa")
@Validated
public class NfaController {
    @Autowired
    NfaService nfaService;

    @GetMapping()
    public List<NfaDetail> getAllNfa(
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
            List<NfaDetail> response = new ArrayList<>();
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
    public NfaStatus createNfa(@RequestBody @Valid CreateNfa createNfa) throws Exception {
        try {
            if (Integer.parseInt(createNfa.getStartDate()) > Integer.parseInt(createNfa.getEndDate())) {
                List<String> message = new ArrayList<>();
                message.add("起始日期 不能大於 結束日期");
                throw new ParamInvalidException(message);
            }
            NfaStatus response = this.nfaService.createNfa(createNfa);
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

    @PutMapping("/{nfaUuid}")
    public NfaStatus updateNfa(
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
            NfaStatus response = this.nfaService.updateNfa(nfaUuid, updateNfa);
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

    @PostMapping("/{nfaUuid}")
    public NfaStatus deleteNfa(
            @PathVariable
            @Pattern(regexp = "^[?=\\d]{17}", message = "nfaUuid 格式錯誤")
            String nfaUuid,
            @RequestBody @Valid DeleteNfa deleteNfa) throws Exception {
        try {
            NfaStatus response = this.nfaService.deleteNfa(nfaUuid, deleteNfa);
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }

    }


}
