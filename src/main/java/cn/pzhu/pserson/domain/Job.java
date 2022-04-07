package cn.pzhu.pserson.domain;

import javax.persistence.*;
import lombok.Data;

@Data
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "dept_id")
  private Integer deptId;

  private String name;

  private String remark;


}