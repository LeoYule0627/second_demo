package com.practice.springsecondphrasepractice.model;

import com.practice.springsecondphrasepractice.model.entity.Bud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudRepository extends JpaRepository<Bud, String> {

    Bud findByBudYmd(String searchDate);
    List<Bud> findByBudYmdBetweenAndBudType(String startDate, String endDate,String budBype);
    List<Bud> findByBudYmdStartingWithAndBudType(String year,String budType);
    List<Bud> findByBudYmdLessThanAndBudTypeOrderByBudYmdDesc(String searchDate, String budType);
    List<Bud> findByBudYmdGreaterThanAndBudTypeOrderByBudYmdAsc(String searchDate, String budType);

}
