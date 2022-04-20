package cn.pzhu.pserson.service.impl;

import cn.pzhu.pserson.dao.dao.*;
import cn.pzhu.pserson.domain.*;
import cn.pzhu.pserson.domain.response.HolidayResDto;
import cn.pzhu.pserson.domain.response.HourResDto;
import cn.pzhu.pserson.domain.response.IndexHeaderResDTO;
import cn.pzhu.pserson.service.UserService;
import cn.pzhu.pserson.util.DateFormate;
import cn.pzhu.pserson.util.MD5Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.pzhu.pserson.util.PinyinUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service
public class UserServiceImpl implements UserService {

  @Resource
  UserMapper userMapper;
  @Resource
  HourMapper hourMapper;
  @Resource
  HolidayMapper holidayMapper;
  @Resource
  PayMapper payMapper;
  @Resource
  HttpServletRequest request;
  @Resource
  EmployeeMapper employeeMapper;

  @Override
  public String repeatName(String name) {
    int num = userMapper.repeatName(name);
    String flag = (num > 0) ? "1" : "0";
    System.out.println("============");
    System.out.println(flag);
    System.out.println("=====================");
    return flag;
  }

  @Override
  public void insertUser(User user) {
    user.setPassword(MD5Util.crypt(user.getPassword()));
    user.setCreatedate(DateFormate.dateToString(new Date()));

    user.setLoginname(getname(user));
    userMapper.insert(user);
  }

  private String getname(User user) {
    String loginname = PinyinUtil.getLoginname(user.getUsername());
    int count = userMapper.selectByName(loginname);
    if(count != 0){
      loginname += ("0"+count);
    }
    return loginname;
  }

  @Override
  public IndexHeaderResDTO countHeader() {
    return userMapper.countHeader();
  }

  @Override
  public PageInfo selectHourList(String content, int pageNum, int pageSize) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
            .doSelectPageInfo(() -> hourMapper.selectHourList(content));
    return pageInfo;
  }

  @Override
  public HourResDto selecthour(int id) {
    HourResDto hour = hourMapper.selecthour(id);
    return hour;
  }

  @Override
  public void hourupdate(int id, String state) {
    Hour hour =new Hour();
    hour.setId(id);
    hour.setState(state);
    hour.setStatetime(LocalDateTime.now());
    HttpSession session = request.getSession();
    int userid = (int) session.getAttribute("userid");
    hour.setStateoperator(userid);
    HourResDto hour1 = hourMapper.selecthour(id);
    Pay pay = payMapper.selectByTime(hour1.getWorktime().substring(0,7));
    if(ObjectUtils.isEmpty(pay)){
      pay = new Pay();
      Employee employee = employeeMapper.selectByPrimaryKey(hour1.getEmpid());
      pay.setEmpid(hour1.getEmpid());
      pay.setBasicpay(employee.getBasepay());
      pay.setPaytime(hour1.getWorktime().substring(0,7));
      pay.setPaysum(employee.getBasepay().divide(new BigDecimal(176),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(hour1.getWorknumber())));
      pay.setCreatetime(LocalDateTime.now());
      payMapper.insert(pay);
    }else {
      pay.setPaysum(pay.getPaysum().add(pay.getBasicpay().divide(new BigDecimal(176),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(hour1.getWorknumber()))));
      payMapper.updateByPrimaryKeySelective(pay);
    }
    hourMapper.updatehour(hour);
  }

  @Override
  public PageInfo holidayList(String content, int pageNum, int pageSize) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
            .doSelectPageInfo(() -> holidayMapper.selectHolidayList(content));
    return pageInfo;
  }

  @Override
  public PageInfo worktimeList(String content, int pageNum, int pageSize) {
    PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize, true)
            .doSelectPageInfo(() -> holidayMapper.selectworktimeList(content));
    return pageInfo;
  }

  @Override
  public HolidayResDto selectHoliday(int id) {
    HolidayResDto hour = holidayMapper.selectHoliday(id);
    return hour;
  }

  @Override
  public void holidayupdate(int id, String state) {
    Holiday holiday =new Holiday();
    holiday.setId(id);
    holiday.setState(state);
    holiday.setStatetime(LocalDateTime.now());
    HttpSession session = request.getSession();
    int userid = (int) session.getAttribute("userid");
    holiday.setStateoperator(userid);
    HolidayResDto holiday1 = holidayMapper.selectHoliday(id);
    Pay pay = payMapper.selectByTime(holiday1.getBegintime().substring(0,7));
    if(ObjectUtils.isEmpty(pay)){
      Employee employee = employeeMapper.selectByPrimaryKey(holiday1.getEmpid());
      pay = new Pay();
      pay.setEmpid(holiday1.getEmpid());
      pay.setBasicpay(employee.getBasepay());
      pay.setPaytime(holiday1.getBegintime().substring(0,7));
      pay.setHoildaypay(new BigDecimal(0).subtract(employee.getBasepay().divide(new BigDecimal(176),2,BigDecimal.ROUND_HALF_UP).multiply(holiday1.getDuration())));
      pay.setPaysum(new BigDecimal(0).subtract(employee.getBasepay().divide(new BigDecimal(176),2,BigDecimal.ROUND_HALF_UP).multiply(holiday1.getDuration())));
      pay.setCreatetime(LocalDateTime.now());
      payMapper.insert(pay);
    }else {
      if(ObjectUtils.isEmpty(pay.getHoildaypay())){
        pay.setHoildaypay(new BigDecimal(0).subtract(pay.getBasicpay().divide(new BigDecimal(176),2,BigDecimal.ROUND_HALF_UP).multiply(holiday1.getDuration())));
      } else {
        pay.setHoildaypay(pay.getHoildaypay().subtract(pay.getBasicpay().divide(new BigDecimal(176),2,BigDecimal.ROUND_HALF_UP).multiply(holiday1.getDuration())));
      }
      pay.setPaysum(pay.getPaysum().subtract(pay.getBasicpay().divide(new BigDecimal(176),2,BigDecimal.ROUND_HALF_UP).multiply(holiday1.getDuration())));
      payMapper.updateByPrimaryKeySelective(pay);
    }
    holidayMapper.updateholiday(holiday);
  }

  @Override
  public void overtimeupdate(int id, String state) {
    Holiday holiday =new Holiday();
    holiday.setId(id);
    holiday.setState(state);
    holiday.setStatetime(LocalDateTime.now());
    HttpSession session = request.getSession();
    int userid = (int) session.getAttribute("userid");
    holiday.setStateoperator(userid);
    HolidayResDto holiday1 = holidayMapper.selectHoliday(id);
    Pay pay = payMapper.selectByTime(holiday1.getBegintime().substring(0,7));
    if(ObjectUtils.isEmpty(pay)){
      Employee employee = employeeMapper.selectByPrimaryKey(holiday1.getEmpid());
      pay = new Pay();
      pay.setEmpid(holiday1.getEmpid());
      pay.setBasicpay(employee.getBasepay());
      pay.setPaytime(holiday1.getBegintime().substring(0,7));
      pay.setOvertimepay(new BigDecimal(15).subtract(holiday1.getDuration()));
      pay.setPaysum(new BigDecimal(15).subtract(holiday1.getDuration()));
      pay.setCreatetime(LocalDateTime.now());
      payMapper.insert(pay);
    }else {
      pay.setOvertimepay(new BigDecimal(15).subtract(holiday1.getDuration()).add(pay.getOvertimepay()));
      pay.setPaysum(pay.getPaysum().subtract(pay.getBasicpay().divide(new BigDecimal(176),2,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(15).subtract(holiday1.getDuration()))));
      payMapper.updateByPrimaryKeySelective(pay);
    }
    holidayMapper.updateholiday(holiday);
  }
}
