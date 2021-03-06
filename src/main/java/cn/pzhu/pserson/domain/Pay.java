package cn.pzhu.pserson.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer empid;
    private String paytime;
    private BigDecimal basicpay;
    private BigDecimal overtimepay;
    private BigDecimal hoildaypay;
    private BigDecimal paysum;
    private LocalDateTime createtime;

}
