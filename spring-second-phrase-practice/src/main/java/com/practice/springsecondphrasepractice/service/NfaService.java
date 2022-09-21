package com.practice.springsecondphrasepractice.service;

import com.practice.springsecondphrasepractice.controller.dto.request.nfa.CreateNfa;
import com.practice.springsecondphrasepractice.controller.dto.request.nfa.DeleteNfa;
import com.practice.springsecondphrasepractice.controller.dto.request.nfa.UpdateNfa;
import com.practice.springsecondphrasepractice.controller.dto.response.nfa.NfaDetail;
import com.practice.springsecondphrasepractice.controller.dto.response.nfa.NfaStatus;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.entity.Nfa;
import com.practice.springsecondphrasepractice.model.NfaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NfaService {
    @Autowired
    NfaRepository nfaRepository;

    public List<NfaDetail> loading(List<Nfa> nfaList) {
        List resultList = new ArrayList<>();
        for (Nfa nfa : nfaList) {
            NfaDetail response = new NfaDetail();
            response.setUuid(nfa.getNfaUuid());
            response.setSubject(nfa.getNfaSubject());
            response.setContent(nfa.getNfaContent());
            response.setEnable(nfa.getNfaEnable());
            response.setCreateTime(nfa.getNfaUTime());
            resultList.add(response);
        }
        return resultList;
    }

    public List<NfaDetail> getAllNfa() throws Exception {
        try {
            List<Nfa> nfaList = this.nfaRepository.findAll();
            if (nfaList.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            nfaList = nfaList.stream().filter(s -> s.getNfaEnable().equals("Y")).collect(Collectors.toList());
            List<NfaDetail> response = loading(nfaList);
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public List<NfaDetail> getNfaRange(String subject, String startDate, String endDate) throws Exception {
        try {
            List<Nfa> nfaList = new ArrayList<>();
            if (subject != null) {
                nfaList = this.nfaRepository.findByNfaSubjectContaining(subject);
                if (startDate != null && endDate == null) {
                    nfaList = nfaList.stream().filter(s -> s.getNfaSTime().equals(startDate)).collect(Collectors.toList());
                } else if (startDate == null && endDate != null) {
                    nfaList = nfaList.stream().filter(s -> s.getNfaETime().equals(endDate)).collect(Collectors.toList());
                } else if (startDate != null && endDate != null) {
                    nfaList = nfaList.stream().filter(s -> s.getNfaSTime().equals(startDate)).collect(Collectors.toList());
                    nfaList = nfaList.stream().filter(s -> s.getNfaETime().equals(endDate)).collect(Collectors.toList());
                }
            } else {
                if (startDate != null && endDate == null) {
                    nfaList = this.nfaRepository.findByNfaSTime(startDate);
                } else if (startDate == null && endDate != null) {
                    nfaList = this.nfaRepository.findByNfaETime(endDate);
                } else if (startDate != null && endDate != null) {
                    nfaList = this.nfaRepository.findByNfaSTime(startDate);
                    nfaList = nfaList.stream().filter(s -> s.getNfaETime().equals(endDate)).collect(Collectors.toList());
                }
            }
            nfaList = nfaList.stream().filter(s -> s.getNfaEnable().equals("Y")).collect(Collectors.toList());
            if (nfaList.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            List<NfaDetail> response = loading(nfaList);
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public NfaStatus createNfa(CreateNfa request) throws Exception {
        try {
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
                return new NfaStatus("新增成功");
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


    public NfaStatus updateNfa(String nfaUuid, UpdateNfa request) throws Exception {
        try {
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
            return new NfaStatus("異動成功");
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public NfaStatus deleteNfa(String nfaUuid, DeleteNfa request) throws Exception {
        try {
            Nfa deleteNfa = this.nfaRepository.findByNfaUuid(nfaUuid);
            if (deleteNfa == null) {
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            deleteNfa.setNfaEnable(request.getEnable());
            deleteNfa.setNfaUTime(LocalDateTime.now());
            this.nfaRepository.save(deleteNfa);

            return new NfaStatus("撤銷成功");
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }
}
