package cn.pzhu.pserson.controller;

import cn.pzhu.pserson.domain.*;
import cn.pzhu.pserson.domain.response.EmployeeResDTO;
import cn.pzhu.pserson.domain.response.HolidayResDto;
import cn.pzhu.pserson.domain.response.HourResDto;
import cn.pzhu.pserson.service.EmployeeService;
import cn.pzhu.pserson.service.RainService;
import cn.pzhu.pserson.service.UserService;
import cn.pzhu.pserson.util.Constants;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private UserService userService;

  @Autowired
  @Qualifier("RainService")
  private RainService rainservice;

  // 如果在目录下输入为空，则跳转到指定链接
  @RequestMapping(value = "/employee/")
  public ModelAndView index2(ModelAndView mv) {
    mv.setViewName("employee/list");
    return mv;
  }

  // 如果在目录下输入任何不存在的参数，则跳转到list
  @RequestMapping(value = "/employee/{formName}")
  public String index2(@PathVariable String formName) {
    String blank = "/employee/list";
    return blank;
  }

  @RequestMapping(value = "/employee/list", method = RequestMethod.GET)
  public String index(Model model, String content,int pageNum,int pageSize) {
    PageInfo pageInfo = employeeService.getEmployee(content,pageNum, pageSize);
    model.addAttribute("list", pageInfo.getList());
    model.addAttribute("pageInfo", pageInfo);
    return "employee/list";
  }

  @RequestMapping(value = "/employee/add", method = RequestMethod.GET)
  public String add(Model model, Integer id) {
    if (id != null) {
//      Employee employee = rainservice.get_EmployeeInfo(id);
      Employee employee = employeeService.getEmployee(id);
      model.addAttribute("job", employee);
    }
    Map<String, Object> info = employeeService.getInfo();
    model.addAttribute("job_list", (List<Job>) info.get("jobs"));
    model.addAttribute("dept_list", (List<Dept>) info.get("depts"));
    return "/employee/add";
  }

  @RequestMapping(value = "/employee/add", method = RequestMethod.POST)
  public ModelAndView add(ModelAndView mv, @ModelAttribute Employee job, Integer id) {
      if (job.getId() != null) {
      employeeService.update(job);
//      rainservice.insert_EmployeeInfo(job);
    } else {
      employeeService.insert(job);
//      rainservice.update_EmployeeInfo(job);
    }
    mv.setViewName("redirect:/employee/list?pageNum=1&pageSize=6");
    return mv;
  }

  @RequestMapping(value = "/employee/delete", method = RequestMethod.GET)
  public String delete(Integer id) {
    if (id != null) {
      rainservice.delete_EmployeeInfo(id);
    }
    return "employee/list";
  }

  @RequestMapping(value = "/employee/updatePassword", method = RequestMethod.GET)
  public String updatePassword(Integer id){
    if(id != null){
      rainservice.updatePassword(id);
    }
    return "employee/list";
  }
  @RequestMapping(value = "/employee/login")
  public ModelAndView login(@RequestParam("loginname") String loginname,
                            @RequestParam("password") String password,
                            HttpSession session,
                            ModelAndView mv){
    Employee employee = rainservice.employeeLogin(loginname, password);
    if (employee != null) {
      session.setAttribute(Constants.EMPLOYEE_SESSION, employee);
      session.setAttribute("userid",employee.getId());
      session.setAttribute("username",employee.getName());
      session.setAttribute("headerr",userService.countHeader());
      mv.setViewName("employee/index");
    } else {
      mv.addObject("message", "登录名或密码错误!请重新输入");
      mv.setViewName("forward:/loginForm");
    }
    return mv;
  }

  @RequestMapping("/employee/employeedetails")
  public String employeedetails(Model model, HttpSession session){
    EmployeeResDTO employee = employeeService.employeedetails((Integer) session.getAttribute("userid"));
    model.addAttribute("employee", employee);
    return "employee/employeedetails";
  }

  @RequestMapping("/employee/empadd")
  public String empadd(Model model, Integer id){
    Employee employee = employeeService.getEmployee(id);
    model.addAttribute("job", employee);

    Map<String, Object> info = employeeService.getInfo();
    model.addAttribute("job_list", (List<Job>) info.get("jobs"));
    model.addAttribute("dept_list", (List<Dept>) info.get("depts"));
    return "employee/empadd";
  }

  @RequestMapping("/employee/logout")
  public ModelAndView logOut(String username, HttpSession session) {
//    User user = (User) session.getAttribute(Constants.USER_SESSION);
    session.removeAttribute(Constants.EMPLOYEE_SESSION);  //删除这个Session
    session.removeAttribute("userid");
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("forward:/loginForm");
    return modelAndView;
  }

  @RequestMapping(value = "/employee/update", method = RequestMethod.POST)
  public ModelAndView update(ModelAndView mv, @ModelAttribute Employee job) {
    employeeService.update(job);
    mv.setViewName("redirect:/employee/employeedetails");
    return mv;
  }

  @RequestMapping("/employee/hour")
  public ModelAndView hour(){
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("/employee/hour");
    return modelAndView;
  }

  @RequestMapping("/employee/hourList")
  public ModelAndView hourList(String content, HttpSession session,int pageNum,int pageSize){

    int userid = (int) session.getAttribute("userid");
    PageInfo pageInfo = employeeService.hourList(userid, content,pageNum,pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("hourlist", pageInfo.getList());
    modelAndView.addObject("pageInfo", pageInfo);
    modelAndView.setViewName("/employee/hourList");
    return modelAndView;
  }

  @RequestMapping("/employee/inhour")
  public ModelAndView inhour(@ModelAttribute Hour hour,HttpSession session){
    employeeService.inhour(hour, (Integer) session.getAttribute("userid"));
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("forward:/employee/hourList?pageNum=1&pageSize=6");
    return modelAndView;
  }

  @RequestMapping("/employee/holidayadd")
  public ModelAndView holidayadd(String sort){
    ModelAndView modelAndView = new ModelAndView();
    if("qingjia".equals(sort)){
      modelAndView.setViewName("/holiday/holidayadd");
    }else {
      modelAndView.setViewName("/overtime/overtimeadd");
    }
    return modelAndView;
  }

  @RequestMapping("/employee/holidayList")
  public ModelAndView holidayList(String content, HttpSession session,int pageNum,int pageSize){

    int userid = (int) session.getAttribute("userid");
    PageInfo pageInfo = employeeService.holidayList(userid, content,pageNum,pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("holidaylist", pageInfo.getList());
    modelAndView.addObject("pageInfo", pageInfo);
    modelAndView.setViewName("/holiday/holidayList");
    return modelAndView;
  }

  @RequestMapping("/employee/insertholiday")
  public ModelAndView insertholiday(@ModelAttribute Holiday holiday, HttpSession session){
    employeeService.insertholiday(holiday, (Integer) session.getAttribute("userid"));
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("forward:/employee/holidayList?pageNum=1&pageSize=6");
    return modelAndView;
  }

  @RequestMapping("/employee/overtimeList")
  public ModelAndView overtimeList(String content, HttpSession session,int pageNum,int pageSize){

    int userid = (int) session.getAttribute("userid");
    PageInfo pageInfo = employeeService.overtimeList(userid, content,pageNum,pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("holidaylist", pageInfo.getList());
    modelAndView.addObject("pageInfo", pageInfo);
    modelAndView.setViewName("/overtime/overtimeList");
    return modelAndView;
  }

  @RequestMapping("/employee/insertovertime")
  public ModelAndView insertovertime(@ModelAttribute Holiday holiday, HttpSession session){
    employeeService.insertholiday(holiday, (Integer) session.getAttribute("userid"));
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("forward:/employee/overtimeList?pageNum=1&pageSize=6");
    return modelAndView;
  }

  @RequestMapping("/employee/paydetail")
  public ModelAndView paydetail(HttpSession session,String content,int pageNum,int pageSize){
    PageInfo pageInfo  = employeeService.paydetail((int) session.getAttribute("userid"),content,pageNum,pageSize);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("paylist", pageInfo.getList());
    modelAndView.addObject("pageInfo", pageInfo);
    modelAndView.setViewName("/pay/payList");
    return modelAndView;
  }

  @RequestMapping("/employee/againHoliday")
  public ModelAndView againHoliday(int id){
    HolidayResDto holidayResDto = employeeService.againHoliday(id);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("dept", holidayResDto);
    modelAndView.setViewName("/holiday/holidayadd");
    return modelAndView;
  }
  @RequestMapping("/employee/againovertime")
  public ModelAndView againovertime(int id){
    HolidayResDto holidayResDto = employeeService.againHoliday(id);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("dept", holidayResDto);
    modelAndView.setViewName("/overtime/overtimeadd");
    return modelAndView;
  }
  @RequestMapping("/employee/againhour")
  public ModelAndView againhour(int id){
    HourResDto hourResDto = employeeService.againhour(id);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("dept", hourResDto);
    modelAndView.setViewName("/employee/hour");
    return modelAndView;
  }

  @RequestMapping("/employee/deleteHoliday")
  public String deleteHoliday(Integer id){
    if (id != null) {
      employeeService.deleteHoliday(id);
    }
    return "employee/holidayList";
  }
  @RequestMapping("/employee/deleteovertime")
  public String deleteovertime(Integer id){
    if (id != null) {
      employeeService.deleteHoliday(id);
    }
    return "employee/overtimeList";
  }
  @RequestMapping("/employee/deletehour")
  public String deletehour(Integer id){
    if (id != null) {
      employeeService.deletehour(id);
    }
    return "employee/hourList";
  }

}
