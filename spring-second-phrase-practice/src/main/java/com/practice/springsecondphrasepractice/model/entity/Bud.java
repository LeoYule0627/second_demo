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
@Table(name = "bud")
public class Bud {
    @Id
    @Column(name = "bud_ymd")
    private String budYmd;
    @Column(name = "bud_type")
    private String budType;
    @Column(name = "bud_u_time")
    private LocalDateTime budUTime;
}
