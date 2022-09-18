package com.practice.springsecondphrasepractice.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudRepository extends JpaRepository<Bud, String> {

    Bud findByBudYmd(String searchDate);

    List<Bud> findByBudYmdBetween(String startDate, String endDate);
    List<Bud> findByBudYmdStartingWith(String year);

    @Query(value = "SELECT bud_ymd FROM bud WHERE bud_ymd < ?1 and bud_type = 'Y' ORDER BY bud_ymd DESC LIMIT 1",nativeQuery = true)
    String getbudPrevYmd(String searchDate);

    @Query(value = "SELECT bud_ymd FROM bud WHERE bud_ymd > ?1 and bud_type = 'Y' ORDER BY bud_ymd ASC LIMIT 1",nativeQuery = true)
    String getbudNextYmd(String searchDate);

}
