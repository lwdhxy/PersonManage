<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>添加工时</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${ctx}/public/css/font.css">
    <link rel="stylesheet" href="${ctx}/public/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/public/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/public/js/xadmin.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  
  <body>
    <div class="x-body">
        <form class="layui-form" method="POST" id="deptForm"  action="${ctx}/employee/inhour">
          <div class="layui-form-item" >
              <label for="worknumber" class="layui-form-label">
                  <span class="x-red">*</span>工时
              </label>
              <div class="layui-input-inline">
                  <input type="number" id="worknumber" name="worknumber" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" placeholder="请输入本月工时">
              </div>
          </div>
            <div class="layui-form-item" >
              <label for="workcontent" class="layui-form-label">
                  <span class="x-red">*</span>日志
              </label>
              <div class="layui-input-inline">
                  <textarea rows="30" cols="20" type="" id="workcontent" name="workcontent" required="" lay-verify="required"
                            autocomplete="off" class="layui-input" placeholder="工作内容"></textarea>
              </div>
          </div>

          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
              </label>
              <input type="submit" value=" 提交" class="layui-btn" lay-filter="add" lay-submit=""/>
                 
          </div>
      </form>
    </div>
    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;


          //监听提交
          form.on('submit(add)', function(data){
              //自定义验证规则
              form.verify({
                  worknumber: function(value){
                      console.log(value);
                      if(value > 40){
                          return '工时不能大于40个小时';
                      }
                  }
              });
              layer.alert("提交成功", {icon: 6},function () {
            	document.getElementById('deptForm').submit();
                // 获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
            });
            console.log(data);
            //发异步，把数据提交给php
            return false;
          });
        });
    </script>
    
  </body>

</html>