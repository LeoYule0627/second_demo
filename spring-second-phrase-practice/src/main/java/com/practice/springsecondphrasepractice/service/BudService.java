package com.practice.springsecondphrasepractice.service;

import com.practice.springsecondphrasepractice.controller.dto.request.CreateDate;
import com.practice.springsecondphrasepractice.controller.dto.request.UpdateDate;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.Bud;
import com.practice.springsecondphrasepractice.model.BudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BudService {

    @Autowired
    BudRepository budRepository;

    public List<Bud> getAllDate() throws Exception {
        try {
            List<Bud> response = this.budRepository.findAll();
            if (response.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            return response;
        } catch (Exception e) {
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    public List<Bud> getDateRange(String startDate, String endDate) throws Exception {
        try {
            List<Bud> response = this.budRepository.findByBudYmdBetween(startDate, endDate);
            if (response.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            response = response.stream().filter(s -> s.getBudType().equals("Y")).collect(Collectors.toList());
            return response;
        } catch (Exception e) {
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }


    public Bud getOneDate(String searchDate) throws Exception {
        try {
            Bud response = this.budRepository.findByBudYmd(searchDate);
            if (response == null) {
                throw new DataNotFoundException("資料不存在");
            }
            return response;
        } catch (Exception e) {
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    public List<Bud> getYearDate(String year) throws Exception {
        try{
            List<Bud> response = this.budRepository.findByBudYmdStartingWith(year);
            if (response.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            return response;
        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }
    }

    public Map getBeforeAndAfter(String searchDate) throws Exception {
        try{
            String budPrevYmd = this.budRepository.getbudPrevYmd(searchDate);
            String budNextYmd = this.budRepository.getbudNextYmd(searchDate);
            if (budPrevYmd == null || budNextYmd == null) {
                throw new DataNotFoundException("資料不存在");
            }
            LinkedHashMap response = new LinkedHashMap();
            response.put("budYmd", searchDate);
            response.put("budPrevYmd", budPrevYmd);
            response.put("budNextYmd", budNextYmd);
            return response;
        }catch (Exception e){
            if(e instanceof DataNotFoundException){
                throw e;
            }
            throw new Exception();
        }

    }

    public Map createDate(CreateDate request) throws Exception {
        try{
            Map response = new HashMap<>();
            Bud check = this.budRepository.findByBudYmd(request.getBudYmd());
            if (check == null) {
                Bud createBud = new Bud();
                createBud.setBudYmd(request.getBudYmd());
                createBud.setBudType(request.getBudType());
                createBud.setBudUTime(LocalDateTime.now());
                this.budRepository.save(createBud);
                response.put("message", "新增成功");
                return response;
            }
            List<String> message = new ArrayList<>();
            message.add("資料已存在");
            throw new ParamInvalidException(message);
        }catch (Exception e){
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }

    public Map updateDateType(String budYmd, UpdateDate request) throws Exception {
        try{
            Map response = new HashMap<>();
            Bud updateBud = this.budRepository.findByBudYmd(budYmd);
            if (updateBud == null) {
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            updateBud.setBudType(request.getBudType());
            updateBud.setBudUTime(LocalDateTime.now());
            this.budRepository.save(updateBud);
            response.put("message", "異動成功");
            return response;
        }catch (Exception e){
            if(e instanceof ParamInvalidException){
                throw e;
            }
            throw new Exception();
        }
    }
}
