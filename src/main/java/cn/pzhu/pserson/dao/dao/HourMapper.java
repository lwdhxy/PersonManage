package cn.pzhu.pserson.dao.dao;

import cn.pzhu.pserson.domain.Hour;
import cn.pzhu.pserson.domain.response.HourResDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface HourMapper extends BaseMapper<Hour> {

    List<HourResDto> selectKey(@Param("userid") int userid, @Param("worktime") String worktime);
}
