package cn.pzhu.pserson.service;

import cn.pzhu.pserson.domain.Employee;
import cn.pzhu.pserson.domain.Holiday;
import cn.pzhu.pserson.domain.Hour;
import cn.pzhu.pserson.domain.response.EmployeeResDTO;
import cn.pzhu.pserson.domain.response.HolidayResDto;
import cn.pzhu.pserson.domain.response.HourResDto;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

  Map<String,Object> getInfo();

  PageInfo getEmployee(int pageNum, int pageSize);

  void insert(Employee employee);

  void update(Employee employee);

  PageInfo getEmployee(String content, int pageNum, int pageSize);

  Employee getEmployee(Integer id);

  EmployeeResDTO employeedetails(Integer userid);

    void inhour(Hour hour, int userid);

  PageInfo hourList(int userid, String worktime, int pageNum, int pageSize);

    PageInfo holidayList(int userid, String content, int pageNum, int pageSize);

  void insertholiday(Holiday holiday, Integer userid);

  PageInfo overtimeList(int userid, String content, int pageNum, int pageSize);

  PageInfo paydetail(int userid, String content, int pageNum, int pageSize);

    HolidayResDto againHoliday(int id);

  void deleteHoliday(Integer id);

  HourResDto againhour(int id);

  void deletehour(Integer id);
}
