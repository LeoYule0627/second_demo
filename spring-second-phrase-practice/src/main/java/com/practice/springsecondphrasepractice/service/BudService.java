package com.practice.springsecondphrasepractice.service;

import com.practice.springsecondphrasepractice.controller.dto.request.bud.CreateBud;
import com.practice.springsecondphrasepractice.controller.dto.request.bud.UpdateBud;
import com.practice.springsecondphrasepractice.controller.dto.response.bud.BudRange;
import com.practice.springsecondphrasepractice.controller.dto.response.bud.BudStatus;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.entity.Bud;
import com.practice.springsecondphrasepractice.model.BudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public List<Bud> getDateRange(String startDate, String endDate) throws Exception {
        try {
            List<Bud> response = this.budRepository.findByBudYmdBetweenAndBudType(startDate, endDate, "Y");
            if (response.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
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
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public List<Bud> getYearDate(String year) throws Exception {
        try {
            List<Bud> response = this.budRepository.findByBudYmdStartingWithAndBudType(year, "Y");
            if (response.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public BudRange getBeforeAndAfter(String searchDate) throws Exception {
        try {
            List<Bud> budPrevYmd = this.budRepository.findByBudYmdLessThanAndBudTypeOrderByBudYmdDesc(searchDate, "Y");
            List<Bud> budNextYmd = this.budRepository.findByBudYmdGreaterThanAndBudTypeOrderByBudYmdAsc(searchDate, "Y");
            if (budPrevYmd == null || budNextYmd == null) {
                throw new DataNotFoundException("資料不存在");
            }
            BudRange budRange = new BudRange();
            budRange.setBudYmd(searchDate);
            budRange.setBudPrevYmd((budPrevYmd.size() != 0) ? budPrevYmd.get(0).getBudYmd() : "無");
            budRange.setBudNextYmd((budNextYmd.size() != 0) ? budNextYmd.get(0).getBudYmd() : "無");
            return budRange;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }

    }

    public BudStatus createDate(CreateBud request) throws Exception {
        try {
            Bud check = this.budRepository.findByBudYmd(request.getBudYmd());
            if (check == null) {
                Bud createBud = new Bud();
                createBud.setBudYmd(request.getBudYmd());
                createBud.setBudType(request.getBudType());
                createBud.setBudUTime(LocalDateTime.now());
                this.budRepository.save(createBud);
                return new BudStatus("新增成功");
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

    public BudStatus updateDateType(String budYmd, UpdateBud request) throws Exception {
        try {
            Bud updateBud = this.budRepository.findByBudYmd(budYmd);
            if (updateBud == null) {
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            updateBud.setBudType(request.getBudType());
            updateBud.setBudUTime(LocalDateTime.now());
            this.budRepository.save(updateBud);
            return new BudStatus("異動成功");
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }
}
