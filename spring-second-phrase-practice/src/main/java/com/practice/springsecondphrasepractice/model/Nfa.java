package com.practice.springsecondphrasepractice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nfa")
public class Nfa {
    @Id
    @Column(name = "nfa_uuid")
    private String nfaUuid;
    @Column(name = "nfa_subject")
    private String nfaSubject;
    @Column(name = "nfa_content")
    private String nfaContent;
    @Column(name = "nfa_enable")
    private String nfaEnable;
    @Column(name = "nfa_s_time")
    private String nfaSTime;
    @Column(name = "nfa_e_time")
    private String nfaETime;
    @Column(name = "nfa_u_time")
    private LocalDateTime nfaUTime;
}