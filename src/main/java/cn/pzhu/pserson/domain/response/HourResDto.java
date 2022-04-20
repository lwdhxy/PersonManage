package cn.pzhu.pserson.domain.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class HourResDto {

    private Integer id;
    private Integer empid;
    private String empname;
    private String worktime;
    private Integer worknumber;
    private String workcontent;
    //已提交、审批通过、审批退回
    private String state;
    private String name;
    private LocalDateTime statetime;
    private LocalDateTime createtime;
}
