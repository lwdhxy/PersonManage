package cn.pzhu.pserson.domain.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HolidayResDto {

    private Integer id;
    private Integer empid;
    private String empname;
    private String begintime;
    private BigDecimal duration;
    private String cause;
    private String state;
    private String name;
    private Integer stateoperator;
    private LocalDateTime statetime;
    private LocalDateTime createtime;
}
