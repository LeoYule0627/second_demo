package com.practice.springsecondphrasepractice.service;

import com.practice.springsecondphrasepractice.controller.dto.request.CreateNfa;
import com.practice.springsecondphrasepractice.controller.dto.request.DeleteNfa;
import com.practice.springsecondphrasepractice.controller.dto.request.UpdateNfa;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.Nfa;
import com.practice.springsecondphrasepractice.model.NfaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NfaService {
    @Autowired
    NfaRepository nfaRepository;

    public List<LinkedHashMap> allFormat(List<Nfa> nfaList) {
        List resultList = new ArrayList<>();
        for (Nfa nfa : nfaList) {
            LinkedHashMap nfaMap = new LinkedHashMap<>();
            nfaMap.put("nfaUuid", nfa.getNfaUuid());
            nfaMap.put("nfaSubject", nfa.getNfaSubject());
            nfaMap.put("nfaContent", nfa.getNfaContent());
            nfaMap.put("nfaEnable", nfa.getNfaEnable());
            nfaMap.put("nfaUTime", nfa.getNfaUTime());
            resultList.add(nfaMap);
        }
        return resultList;
    }

    public List<LinkedHashMap> conditionFormat(List<Nfa> nfaList) {
        List resultList = new ArrayList<>();
        for (Nfa nfa : nfaList) {
            LinkedHashMap nfaMap = new LinkedHashMap<>();
            nfaMap.put("uuid", nfa.getNfaUuid());
            nfaMap.put("subject", nfa.getNfaSubject());
            nfaMap.put("content", nfa.getNfaContent());
            nfaMap.put("enable", nfa.getNfaEnable());
            nfaMap.put("createTime", nfa.getNfaUTime());
            resultList.add(nfaMap);
        }
        return resultList;
    }


    public List<LinkedHashMap> getAllNfa() throws Exception {
        try {
            List<Nfa> nfaList = this.nfaRepository.findAll();
            if (nfaList.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            nfaList = nfaList.stream().filter(s -> s.getNfaEnable().equals("Y")).collect(Collectors.toList());
            List<LinkedHashMap> response = allFormat(nfaList);
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public List<LinkedHashMap> getNfaRange(String subject, String startDate, String endDate) throws Exception {
        try {
            List<Nfa> nfaList = new ArrayList<>();
            if (subject != null) {
                nfaList = this.nfaRepository.findByNfaSubjectContaining(subject);
            }

            if (startDate != null && endDate == null) {
                nfaList = this.nfaRepository.findByNfaSTime(startDate);
            } else if (startDate == null && endDate != null) {
                nfaList = this.nfaRepository.findByNfaETime(endDate);
            } else if (startDate != null && endDate != null) {
                nfaList = this.nfaRepository.findByNfaSTime(startDate);
                nfaList = nfaList.stream().filter(s -> s.getNfaETime().equals(endDate)).collect(Collectors.toList());
            }
            nfaList = nfaList.stream().filter(s -> s.getNfaEnable().equals("Y")).collect(Collectors.toList());
            if (nfaList.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            List<LinkedHashMap> response = conditionFormat(nfaList);
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public Map createNfa(CreateNfa request) throws Exception {
        try {
            Map response = new HashMap();
            String nfaUuid = request.getStartDate() + "142500123";
            Nfa check = this.nfaRepository.findByNfaUuid(nfaUuid);
            if (check == null) {
                Nfa createNfa = new Nfa();
                createNfa.setNfaUuid(nfaUuid);
                createNfa.setNfaSubject(request.getSubject());
                createNfa.setNfaContent(request.getContent());
                createNfa.setNfaEnable(request.getEnable());
                createNfa.setNfaSTime(request.getStartDate());
                createNfa.setNfaETime(request.getEndDate());
                createNfa.setNfaUTime(LocalDateTime.now());
                this.nfaRepository.save(createNfa);
                response.put("message", "新增成功");
                return response;
            }
            List<String> message = new ArrayList<>();
            message.add("資料已存在");
            throw new ParamInvalidException(message);
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }


    public Map updateNfa(String nfaUuid, UpdateNfa request) throws Exception{
        try{
            Map response = new HashMap<>();
            Nfa updateNfa = this.nfaRepository.findByNfaUuid(nfaUuid);
            if (updateNfa == null) {
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            updateNfa.setNfaSubject(request.getSubject());
            updateNfa.setNfaContent(request.getContent());
            updateNfa.setNfaEnable(request.getEnable());
            updateNfa.setNfaSTime(request.getStartDate());
            updateNfa.setNfaETime(request.getEndDate());
            updateNfa.setNfaUTime(LocalDateTime.now());
            this.nfaRepository.save(updateNfa);
            response.put("message", "異動成功");
            return response;
        }catch (Exception e){
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

    public Map deleteNfa(String nfaUuid, DeleteNfa request) throws Exception{
        try{
            Map response = new HashMap<>();
            Nfa deleteNfa = this.nfaRepository.findByNfaUuid(nfaUuid);
            if(deleteNfa == null){
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            deleteNfa.setNfaEnable(request.getEnable());
            deleteNfa.setNfaUTime(LocalDateTime.now());
            this.nfaRepository.save(deleteNfa);
            response.put("message", "異動成功");
            return response;
        }catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }
}
