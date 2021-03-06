package cn.pzhu.pserson.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer empid;
    private String sort;
    private String begintime;
    private BigDecimal duration;
    private String cause;
    private String state;
    private Integer stateoperator;
    private LocalDateTime statetime;
    private LocalDateTime createtime;
}
