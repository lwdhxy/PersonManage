package cn.pzhu.pserson.service.impl;

import cn.pzhu.pserson.dao.dao.*;
import cn.pzhu.pserson.domain.*;
import cn.pzhu.pserson.domain.response.EmployeeResDTO;
import cn.pzhu.pserson.domain.response.HolidayResDto;
import cn.pzhu.pserson.domain.response.HourResDto;
import cn.pzhu.pserson.service.EmployeeService;
import cn.pzhu.pserson.util.PinyinUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class EmployeeServiceImpl implements EmployeeService {


  @Resource
  JobMapper jobMapper;

  @Resource
  DeptMapper deptMapper;

  @Resource
  EmployeeMapper employeeMapper;
  @Resource
  HourMapper hourMapper;
  @Resource
  HolidayMapper holidayMapper;

  @Override
  public Map<String, Object> getInfo() {
    List<Job> jobs = jobMapper.get_List();
    List<Dept> depts = deptMapper.selectAllDept();
    Map<String,Object> map = new HashMap<>();
    map.put("jobs",jobs);
    map.put("depts",depts);
    return map;
  }

  @Override
  public PageInfo getEmployee(int pageNum,int pageSize) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
        .doSelectPageInfo(() -> employeeMapper.getEm());
    return pageInfo;
  }

  @Override
  public void insert(Employee employee) {
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
    employee.setCreateDate(format.format(date));
    employee.setLoginpassword("0000");
    employee.setLoginname(getname(employee.getName()));
    employeeMapper.insert(employee);
  }
  private String getname(String user) {
    String loginname = PinyinUtil.getLoginname(user);
    int count = employeeMapper.selectByName(loginname);
    if(count != 0){
      loginname += ("0"+count);
    }
    return loginname;
  }

  @Override
  public void update(Employee employee) {
    Employee employee1 = employeeMapper.selectByPrimaryKey(employee.getId());
    if(!employee1.getBasepay().equals("")){
      if(employee1.getBasepay() != employee.getBasepay()){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        int userid = (int) request.getSession().getAttribute("userid");
        employee.setUpdatepayoperator(userid);
        employee.setUpdatepaytime(LocalDateTime.now());
      }
    }
    employeeMapper.updateByPrimaryKeySelective(employee);
  }

  @Override
  public PageInfo getEmployee(String content, int pageNum, int pageSize) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
        .doSelectPageInfo(() -> employeeMapper.getEmployees(content));
    return pageInfo;
  }

  @Override
  public Employee getEmployee(Integer id) {
    return employeeMapper.selectByPrimaryKey(id);
  }

  @Override
  public EmployeeResDTO employeedetails(Integer id) {

    return employeeMapper.employeedetails(id);
  }

  @Override
  public void inhour(Hour hour, int userid) {
    hour.setEmpid(userid);
    hour.setCreatetime(LocalDateTime.now());
    hour.setState("submitted");
    if(ObjectUtils.isEmpty(hour.getId())){
      hourMapper.insertSelective(hour);
    }else {
      hourMapper.updateByPrimaryKeySelective(hour);
    }
  }

  @Override
  public PageInfo hourList(int userid, String worktime,int pageNum,int pageSize ) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
            .doSelectPageInfo(() -> hourMapper.selectKey(userid, worktime));
    return pageInfo;
  }

  @Override
  public PageInfo holidayList(int userid, String content, int pageNum, int pageSize) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
            .doSelectPageInfo(() -> holidayMapper.holidayList(userid, content));
    return pageInfo;
  }

  @Override
  public void
  insertholiday(Holiday holiday, Integer userid) {
    holiday.setEmpid(userid);
    holiday.setCreatetime(LocalDateTime.now());
    holiday.setState("submitted");
    if(ObjectUtils.isEmpty(holiday.getId())){
      holidayMapper.insertSelective(holiday);
    }else {
      holidayMapper.updateByPrimaryKeySelective(holiday);
    }

  }

  @Override
  public PageInfo overtimeList(int userid, String content, int pageNum, int pageSize) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
            .doSelectPageInfo(() -> holidayMapper.overtimeList(userid, content));
    return pageInfo;
  }

  @Override
  public PageInfo paydetail(int userid, String content, int pageNum, int pageSize) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
            .doSelectPageInfo(() -> holidayMapper.paydetail(userid, content));
    return pageInfo;
  }

  @Override
  public HolidayResDto againHoliday(int id) {
    return holidayMapper.selectHoliday(id);
  }

  @Override
  public void deleteHoliday(Integer id) {
    holidayMapper.deleteHoliday(id);
  }

  @Override
  public HourResDto againhour(int id) {
    return hourMapper.selecthour(id);
  }

  @Override
  public void deletehour(Integer id) {
    hourMapper.deleteByPrimaryKey(id);
  }

}
