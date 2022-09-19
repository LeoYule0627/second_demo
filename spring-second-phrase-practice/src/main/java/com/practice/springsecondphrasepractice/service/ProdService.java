package com.practice.springsecondphrasepractice.service;

import com.practice.springsecondphrasepractice.controller.dto.request.CreateProd;
import com.practice.springsecondphrasepractice.controller.dto.request.DeleteProd;
import com.practice.springsecondphrasepractice.controller.dto.request.UpdateProd;
import com.practice.springsecondphrasepractice.exception.DataNotFoundException;
import com.practice.springsecondphrasepractice.exception.ParamInvalidException;
import com.practice.springsecondphrasepractice.model.Prod;
import com.practice.springsecondphrasepractice.model.ProdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdService {

    @Autowired
    ProdRepository prodRepository;

    public List<LinkedHashMap> loading(List<Prod> prodList) {
        List resultList = new ArrayList();
        LinkedHashMap prodMap = new LinkedHashMap();
        for (Prod prod : prodList) {
            prodMap.put("prodId", prod.getProdId());
            prodMap.put("prodKind", prod.getProdKind());
            prodMap.put("prodName", prod.getProdName());
            prodMap.put("prodEname", prod.getProdEname());
            prodMap.put("prodCcy", prod.getProdCcy());
            prodMap.put("prodEnable", prod.getProdEnable());
            resultList.add(prodMap);
        }
        return resultList;
    }

    public LinkedHashMap loading(Prod prodList) {
        LinkedHashMap prodMap = new LinkedHashMap();
        prodMap.put("prodId", prodList.getProdId());
        prodMap.put("prodKind", prodList.getProdKind());
        prodMap.put("prodName", prodList.getProdName());
        prodMap.put("prodEname", prodList.getProdEname());
        prodMap.put("prodCcy", prodList.getProdCcy());
        prodMap.put("prodEnable", prodList.getProdEnable());
        return prodMap;
    }

    public List<LinkedHashMap> getAllProd() throws Exception {
        try {
            List<Prod> prodList = this.prodRepository.findAll();
            if (prodList.isEmpty()) {
                throw new DataNotFoundException("資料不存在");
            }
            List<LinkedHashMap> response = loading(prodList);
            return response;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public List<LinkedHashMap> getKind(String searchKind) throws Exception {
        try {
            List<Prod> prodList = this.prodRepository.findByProdKind(searchKind);
            if (prodList.isEmpty()) {
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            prodList = prodList.stream().filter(s -> s.getProdEnable().equals("Y")).collect(Collectors.toList());
            List<LinkedHashMap> response = loading(prodList);
            return response;
        } catch (Exception e) {
            throw new Exception();
        }
    }


    public List<LinkedHashMap> getCcy(String searchCcy) throws Exception {
        try {
            List<Prod> prodList = this.prodRepository.findByProdCcy(searchCcy);
            if (prodList.isEmpty()) {
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            prodList = prodList.stream().filter(s -> s.getProdEnable().equals("Y")).collect(Collectors.toList());
            List<LinkedHashMap> response = loading(prodList);
            return response;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public LinkedHashMap getOneProd(String searchProdId) throws Exception {
        try {
            Prod prodList = this.prodRepository.findByProdId(searchProdId);
            if (prodList == null) {
                throw new DataNotFoundException("資料不存在");
            }
            LinkedHashMap response = loading(prodList);
            return response;
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public Map createProd(CreateProd request) throws Exception {
        try {
            Map response = new HashMap();
            String prodId = request.getProdKind() + "_" + request.getProdCcy();
            Prod check = this.prodRepository.findByProdId(prodId);
            if (check == null) {
                Prod createProd = new Prod();
                createProd.setProdId(prodId);
                createProd.setProdKind(request.getProdKind());
                createProd.setProdName(request.getProdName());
                createProd.setProdEname(request.getProdEname());
                createProd.setProdEnable(request.getProdEnable());
                createProd.setProdCcy(request.getProdCcy());
                createProd.setProdITime(LocalDateTime.now());
                createProd.setProdUTime(LocalDateTime.now());
                this.prodRepository.save(createProd);
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

    public Map updateProdEnable(String prodId, UpdateProd request) throws Exception {
        try {
            Map response = new HashMap();
            Prod updateProd = this.prodRepository.findByProdId(prodId);
            if (updateProd == null) {
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            updateProd.setProdEnable(request.getProdEnable());
            updateProd.setProdName(request.getProdName());
            updateProd.setProdEname(request.getProdEname());
            updateProd.setProdUTime(LocalDateTime.now());
            this.prodRepository.save(updateProd);
            response.put("message", "異動成功");
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }

    public Map deleteProdEnable(String prodId, DeleteProd request) throws Exception {
        try {
            Map response = new HashMap<>();
            Prod deleteProd = this.prodRepository.findByProdId(prodId);
            if (deleteProd == null) {
                List<String> message = new ArrayList<>();
                message.add("資料不存在");
                throw new ParamInvalidException(message);
            }
            deleteProd.setProdEnable(request.getProdEnable());
            deleteProd.setProdUTime(LocalDateTime.now());
            this.prodRepository.save(deleteProd);
            response.put("message", "註銷成功");
            return response;
        } catch (Exception e) {
            if (e instanceof ParamInvalidException) {
                throw e;
            }
            throw new Exception();
        }
    }
}
