package cn.pzhu.pserson.service.impl;

import cn.pzhu.pserson.dao.dao.UserMapper;
import cn.pzhu.pserson.domain.User;
import cn.pzhu.pserson.domain.response.IndexHeaderResDTO;
import cn.pzhu.pserson.service.UserService;
import cn.pzhu.pserson.util.DateFormate;
import cn.pzhu.pserson.util.MD5Util;
import java.util.Date;
import javax.annotation.Resource;

import cn.pzhu.pserson.util.PinyinUtil;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

  @Resource
  UserMapper userMapper;

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
    String loginname = PinyinUtil.getLoginname(user.getUsername());
    int count = userMapper.selectByName(loginname);
    if(count != 0){
      loginname += ("0"+count);
    }
    user.setLoginname(loginname);
    userMapper.insert(user);
  }

  @Override
  public IndexHeaderResDTO countHeader() {
    return userMapper.countHeader();
  }
}
