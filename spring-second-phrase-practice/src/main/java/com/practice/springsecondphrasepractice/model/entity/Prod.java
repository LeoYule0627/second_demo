package com.practice.springsecondphrasepractice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prod")
public class Prod {
    @Id
    @Column(name = "prod_id")
    private String prodId;
    @Column(name = "prod_kind")
    private String prodKind;
    @Column(name = "prod_name")
    private String prodName;
    @Column(name = "prod_ename")
    private String prodEname;
    @Column(name = "prod_ccy")
    private String prodCcy;
    @Column(name = "prod_enable")
    private String prodEnable;
    @Column(name = "prod_i_time")
    private LocalDateTime prodITime;
    @Column(name = "prod_u_time")
    private LocalDateTime prodUTime;
}
