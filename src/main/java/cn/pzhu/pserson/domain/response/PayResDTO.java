package cn.pzhu.pserson.domain.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayResDTO {

    private Integer id;
    private Integer empid;
    private String paytime;
    private String empname;
    private String name;
    private BigDecimal basicpay;
    private BigDecimal overtimepay;
    private BigDecimal hoildaypay;
    private BigDecimal paysum;
    private LocalDateTime createtime;

}
