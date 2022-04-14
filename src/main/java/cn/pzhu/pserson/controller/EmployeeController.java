package cn.pzhu.pserson.controller;

import cn.pzhu.pserson.domain.Dept;
import cn.pzhu.pserson.domain.Employee;
import cn.pzhu.pserson.domain.Hour;
import cn.pzhu.pserson.domain.Job;
import cn.pzhu.pserson.domain.response.EmployeeResDTO;
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
  public ModelAndView hourList(String worktime, HttpSession session){
    int userid = (int) session.getAttribute("userid");
    List<HourResDto> hourlist = employeeService.hourList(userid, worktime);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("hourlist", hourlist);
    modelAndView.setViewName("/employee/hourList");
    return modelAndView;
  }

  @RequestMapping("/employee/inhour")
  public ModelAndView inhour(@ModelAttribute Hour hour,HttpSession session){
    employeeService.inhour(hour, (Integer) session.getAttribute("userid"));
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("forward:/employee/hourList");
    return modelAndView;
  }

}
