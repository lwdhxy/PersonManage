package cn.pzhu.pserson.service;

import cn.pzhu.pserson.domain.User;
import cn.pzhu.pserson.domain.response.HolidayResDto;
import cn.pzhu.pserson.domain.response.HourResDto;
import cn.pzhu.pserson.domain.response.IndexHeaderResDTO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface UserService {

  String repeatName(String name);

  void insertUser(User user);

  IndexHeaderResDTO countHeader();

  PageInfo selectHourList(String content, int pageNum, int pageSize);

    HourResDto selecthour(int id);

    void hourupdate(int id, String state);

    PageInfo holidayList(String content, int pageNum, int pageSize);

  PageInfo worktimeList(String content, int pageNum, int pageSize);

  HolidayResDto selectHoliday(int id);

  void holidayupdate(int id, String state);

  void overtimeupdate(int id, String state);
}
