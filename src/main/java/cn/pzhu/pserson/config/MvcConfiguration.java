package cn.pzhu.pserson.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Configuration
//@EnableWebMvc
//@ComponentScan
@Controller
public class MvcConfiguration implements Converter<String, Date> {
  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//  @Bean
//  public InternalResourceViewResolver viewResolver() {
//
//    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//    viewResolver.setPrefix("/WEB-INF/page/");
//    viewResolver.setSuffix(".html");
//    return viewResolver;
//  }

  // 首先注入一个ServerEndpointExporterBean,该Bean会自动注册使用@ServerEndpoint注解申明的websocket endpoint。
  @Bean
  public ServerEndpointExporter serverEndpointExporter(){
    return new ServerEndpointExporter();
  }


  @Override
  public Date convert(String s) {
    if (s!=null&&!"".equals(s)){

        //解析参数
      Date date= null;
      try {
        date = sdf.parse(s);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return date;
    }
    return null;
  }

  @Override
  public JavaType getInputType(TypeFactory typeFactory) {
    return null;
  }

  @Override
  public JavaType getOutputType(TypeFactory typeFactory) {
    return null;
  }
}
