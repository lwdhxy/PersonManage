package cn.pzhu.pserson.dao.dao;

import cn.pzhu.pserson.domain.Pay;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

public interface PayMapper extends BaseMapper<Pay> {

    @Select("select * from pay where paytime like concat(#{time},'%')")
    Pay selectByTime(String time);
}
