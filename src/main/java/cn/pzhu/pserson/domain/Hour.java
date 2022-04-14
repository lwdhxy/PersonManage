package cn.pzhu.pserson.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hour {
    private int id;
    private int empid;
    private String worktime;
    private int worknumber;
    private String workcontent;
    //已提交、审批通过、审批退回
    private String state;
    private int stateoperator;
    private LocalDateTime statetime;
    private LocalDateTime createtime;
}
