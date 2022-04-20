package cn.pzhu.pserson.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Hour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="empid")
    private Integer empid;

    private String worktime;

    @Column(name="worknumber")
    private Integer worknumber;

    private String workcontent;
    //已提交、审批通过、审批退回
    private String state;
    private Integer stateoperator;
    private LocalDateTime statetime;
    private LocalDateTime createtime;
}
