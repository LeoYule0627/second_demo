package com.practice.springsecondphrasepractice.controller;

import com.practice.springsecondphrasepractice.controller.dto.request.AesRequest;
import com.practice.springsecondphrasepractice.service.Aes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/api/v2/")
public class AesController {

    @Autowired
    Aes aes;

    @PostMapping("encode/aes/ecb")
    public Map aesECBEncode(@RequestBody AesRequest aesRequest) throws Exception {
        String encryptStr = aes.aesECBEncode(aesRequest.getRequest());
        Map response = new HashMap<>();
        response.put("response" ,encryptStr);
        return response;
    }

    @PostMapping("/decode/aes/ecb")
    public Map aesECBDecode(@RequestBody AesRequest aesRequest) throws Exception {
        String encryptStr = aes.aesECBDecode(aesRequest.getRequest());
        Map response = new HashMap<>();
        response.put("response" ,encryptStr);
        return response;
    }

    @PostMapping("encode/aes/cbc")
    public Map aesCBCEncode(@RequestBody AesRequest aesRequest) throws Exception {
        String encryptStr = aes.aesCBCEncode(aesRequest.getRequest());
        Map response = new HashMap<>();
        response.put("response" ,encryptStr);
        return response;
    }

    @PostMapping("/decode/aes/cbc")
    public Map aesCBCDecode(@RequestBody AesRequest aesRequest) throws Exception {
        String encryptStr = aes.aesCBCDecode(aesRequest.getRequest());
        Map response = new HashMap<>();
        response.put("response" ,encryptStr);
        return response;
    }
}
