package cn.pzhu.pserson.domain.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HourResDto {

    private int id;
    private String empname;
    private LocalDateTime worktime;
    private int worknumber;
    private String workcontent;
    //已提交、审批通过、审批退回
    private String state;
    private String name;
    private LocalDateTime statetime;
    private LocalDateTime createtime;
}
