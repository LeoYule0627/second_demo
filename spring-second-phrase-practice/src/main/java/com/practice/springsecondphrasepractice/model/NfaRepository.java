package com.practice.springsecondphrasepractice.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NfaRepository extends JpaRepository<Nfa, String> {

    List<Nfa> findByNfaSubjectContaining(String subject);

    List<Nfa> findByNfaSTime(String startDate);

    List<Nfa> findByNfaETime(String endDate);

    Nfa findByNfaUuid(String nfaUuid);
}
