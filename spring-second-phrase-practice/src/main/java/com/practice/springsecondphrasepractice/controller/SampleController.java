package com.practice.springsecondphrasepractice.controller;

import com.practice.springsecondphrasepractice.controller.dto.request.CreateSampleRequest;
import com.practice.springsecondphrasepractice.controller.dto.response.StatusResponse;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sample")
@Validated
public class SampleController {

    @GetMapping("/{id}")
    public StatusResponse testDataNotFound(@PathVariable @NotBlank String id) throws DataNotFoundException {

       throw new DataNotFoundException("資料不存在");
    }

    @GetMapping("/pathVariableInvalid/{id}")
    public StatusResponse testPathVariableInvalid(@PathVariable @NotBlank  String id)  {

        return new StatusResponse(id);
    }


    @GetMapping("/noParam")
    public StatusResponse testNoParam(@RequestParam @NotEmpty String sampleId) {
        return new StatusResponse("測試成功");
    }


    @PostMapping()
    public StatusResponse testPost(@Valid @RequestBody CreateSampleRequest request) {
        return new StatusResponse("測試成功");
    }

    @PostMapping("/paramInvalid")
    public StatusResponse testParamInvalid(@Valid @RequestBody CreateSampleRequest request) throws ParamInvalidException {
        List<String> errMsgs = new ArrayList<>();
        errMsgs.add("使用者無效");
        errMsgs.add("使用者不在列管內");
        throw new ParamInvalidException(errMsgs);
    }

}





