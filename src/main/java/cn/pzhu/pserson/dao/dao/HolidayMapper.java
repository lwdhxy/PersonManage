package cn.pzhu.pserson.dao.dao;

import cn.pzhu.pserson.domain.Holiday;
import cn.pzhu.pserson.domain.Pay;
import cn.pzhu.pserson.domain.response.HolidayResDto;
import cn.pzhu.pserson.domain.response.PayResDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HolidayMapper extends Mapper<Holiday> {

     List<HolidayResDto> holidayList(@Param("userid") int userid, @Param("content")  String content);

     List<HolidayResDto> overtimeList(@Param("userid") int userid, @Param("content")  String content);

     List<PayResDTO> paydetail(@Param("userid") int userid, @Param("content") String content);

     List<HolidayResDto> selectHolidayList(String content);

     List<HolidayResDto> selectworktimeList(String content);

     HolidayResDto selectHoliday(int id);

     void updateholiday(@Param("holiday") Holiday holiday);

     void deleteHoliday(Integer id);
}
