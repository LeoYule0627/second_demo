package com.practice.springsecondphrasepractice.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdRepository extends JpaRepository<Prod,Integer> {

    List<Prod> findByProdKind(String searchKind);
    List<Prod> findByProdCcy(String searchCcy);
    Prod findByProdId(String searchPordId);
}
