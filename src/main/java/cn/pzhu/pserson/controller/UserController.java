package cn.pzhu.pserson.controller;

import cn.pzhu.pserson.domain.User;
import cn.pzhu.pserson.domain.response.HolidayResDto;
import cn.pzhu.pserson.domain.response.HourResDto;
import cn.pzhu.pserson.service.RainService;
import cn.pzhu.pserson.service.UserService;
import cn.pzhu.pserson.util.Constants;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

  @Autowired
  @Qualifier("RainService")
  private RainService rainservice;

  @Autowired
  private UserService userService;

  // 如果在目录下输入为空，则跳转到指定链接
  @RequestMapping(value = "/user/")
  public ModelAndView index2(ModelAndView mv) {
    mv.setViewName("/user/list");
    return mv;
  }

  @RequestMapping("/")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("loginForm");
    return modelAndView;
  }
//登录
  @RequestMapping(value = "/login")
  public ModelAndView login(@RequestParam("loginname") String loginname,
      @RequestParam("password") String password, @RequestParam("state") String state,
      HttpSession session,
      ModelAndView mv) {
    if("1".equals(state)){
      mv.setViewName("forward:/employee/login");
      return mv;
    }
    User user = rainservice.login(loginname, password);
    if (user != null) {
      session.setAttribute(Constants.USER_SESSION, user);
      session.setAttribute("userid",user.getId());
      session.setAttribute("username",user.getUsername());
      session.setAttribute("headerr",userService.countHeader());
      mv.setViewName("redirect:/index");
    } else {
      mv.addObject("message", "登录名或密码错误!请重新输入");
      mv.setViewName("forward:/loginForm");
    }
    return mv;
  }

  // 如果在目录下输入任何不存在的参数，则跳转到list
  @RequestMapping(value = "/user/{formName}")
  public String index2(@PathVariable String formName) {
    String blank = "/user/list";
    return blank;
  }
//用户列表
  @RequestMapping(value = "/user/list", method = RequestMethod.GET)
  public String index(Model model, String content, int pageNum, int pageSize) {
    Map<String, Object> map = null;
    List<User> job_list = null;
    if (content != null) {
      map = rainservice.get_UserLikeList(content, pageNum, pageSize);
      job_list = (List<User>) map.get("list");
    } else {
       map = rainservice.get_UserList(pageNum, pageSize);
      job_list = (List<User>) map.get("list");
    }
    int size = (int) map.get("size");
    model.addAttribute("size", size);
    model.addAttribute("list", job_list);
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("nums", size / pageSize + 1);
    return "user/list";
  }
//跳转到新增页面
  @RequestMapping(value = "/user/add", method = RequestMethod.GET)
  public String add(Model model, Integer id) {
    if (id != null) {
      User job = rainservice.get_UserInfo(id);
      model.addAttribute("job", job);
      model.addAttribute("update", job.getUsername());
    }
    return "/user/add";
  }

  @RequestMapping(value = "/user/add", method = RequestMethod.POST)
  public ModelAndView add(ModelAndView mv, @ModelAttribute User notice, Integer id) {
    //System.out.println(id);
    if (id != null) {
      rainservice.update_UserInfo(notice);
      mv.getModel().remove("update");
    } else {
      userService.insertUser(notice);
    }
    mv.setViewName("redirect:/user/list?pageNum=1&pageSize=6");
    return mv;
  }

  @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
  public void delete(Integer id) {
    if (id != null) {
      rainservice.delete_UserInfo(id);
    }
  }

  @RequestMapping("/logout")
  public ModelAndView logOut(String username, HttpSession session) {
    session.removeAttribute(Constants.USER_SESSION);  //删除这个Session
    session.removeAttribute("userid");
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("forward:/loginForm");
    return modelAndView;
  }

  @ResponseBody
  @RequestMapping(value = "user/repeat", method = RequestMethod.GET)
  public String repeatName(String name) {
    return userService.repeatName(name);
  }

  @RequestMapping("/user/hourList")
  public ModelAndView hourList(String content, int pageNum, int pageSize){

    PageInfo pageInfo = userService.selectHourList(content,pageNum,pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("hourlist", pageInfo.getList());
    modelAndView.addObject("pageInfo", pageInfo);
    modelAndView.setViewName("/user/hourList");
    return modelAndView;
  }
  @RequestMapping("/user/holidayList")
  public ModelAndView holidayList(String content, int pageNum, int pageSize){

    PageInfo pageInfo = userService.holidayList(content,pageNum,pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("holidaylist", pageInfo.getList());
    modelAndView.addObject("pageInfo", pageInfo);
    modelAndView.setViewName("/user/holidayList");
    return modelAndView;
  }
  @RequestMapping("/user/overtimeList")
  public ModelAndView worktimeList(String content, int pageNum, int pageSize){

    PageInfo pageInfo = userService.worktimeList(content,pageNum,pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("overtimeList", pageInfo.getList());
    modelAndView.addObject("pageInfo", pageInfo);
    modelAndView.setViewName("/user/overtimeList");
    return modelAndView;
  }
  //查看详细工时
  @RequestMapping("/user/selecthour")
  public ModelAndView selecthour(int id){
    HourResDto hourResDto = userService.selecthour(id);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("dept",hourResDto);
    modelAndView.setViewName("/user/houradd");
    return modelAndView;
  }
  //查看详细假期
  @RequestMapping("/user/selectHoliday")
  public ModelAndView selectHoliday(int id){
    HolidayResDto hourResDto = userService.selectHoliday(id);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("dept",hourResDto);
    modelAndView.setViewName("/user/holidayadd");
    return modelAndView;
  }

  @RequestMapping("/user/hourupdate")
  public String hourupdate(int id,String state){
    userService.hourupdate(id,state);
    return "redirect:/user/hourList?pageNum=1&pageSize=6";
  }
  @RequestMapping("/user/holidayupdate")
  public String holidayupdate(int id,String state){
    userService.holidayupdate(id,state);
    return "redirect:/user/holidayList?pageNum=1&pageSize=6";
  }
  @RequestMapping("/user/overtimeupdate")
  public String overtimeupdate(int id,String state){
    userService.overtimeupdate(id,state);
    return "redirect:/user/overtimeList?pageNum=1&pageSize=6";
  }


}
