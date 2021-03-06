<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>个人信息修改</title>
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
        <form class="layui-form" method="POST" id="deptForm"  action="${ctx}/employee/update">
        <input type="hidden" name="id" id="id" value="${job.id }" >
          <div class="layui-form-item" >
              <label for="name" class="layui-form-label">
                  <span class="x-red">*</span>姓名
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="name" name="name" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${job.name }">
              </div>
          </div>
<%--            <div class="layui-form-item" >--%>
<%--              <label for="name" class="layui-form-label">--%>
<%--                  <span class="x-red">*</span>登录名--%>
<%--              </label>--%>
<%--              <div class="layui-input-inline">--%>
<%--                  <input type="text" id="loginname" name="loginname" required="" lay-verify="required"--%>
<%--                  autocomplete="off" class="layui-input" value="${job.loginname }">--%>
<%--              </div>--%>
<%--          </div>--%>
            <div class="layui-form-item" >
              <label for="name" class="layui-form-label">
                  <span class="x-red">*</span>密码
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="loginpassword" name="loginpassword" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${job.loginpassword }">
              </div>
          </div>
          <div class="layui-form-item" >
              <label for="cardId" class="layui-form-label">
                  <span class="x-red">*</span>身份证号码
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="cardId" name="cardId" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${job.cardId }">
              </div>
          </div>
           <div class="layui-form-item">
              <label for="sex" class="layui-form-label">
                  <span class="x-red">*</span>性别
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="sex" name="sex" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${job.sex }">
              </div>
          </div>
           <div class="layui-form-item">
              <label for="education" class="layui-form-label">
                  <span class="x-red">*</span>学历
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="education" name="education" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${job.education }">
              </div>
          </div>
           <div class="layui-form-item">
              <label for="email" class="layui-form-label">
                  <span class="x-red">*</span>邮箱
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="email" name="email" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${job.email }">
              </div>
          </div>
           <div class="layui-form-item">
              <label for="phone" class="layui-form-label">
                  <span class="x-red">*</span>手机
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="phone" name="phone" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${job.phone }">
              </div>
          </div>
          <div class="layui-form-item">
              <label for="address" class="layui-form-label">
                  <span class="x-red">*</span>联系地址
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="address" name="address" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${job.address }">
              </div>
          </div>

            <input type="hidden" value="${job.createDate }" id="createDate" name="createDate">
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
        
          //自定义验证规则
          form.verify({
            nikename: function(value){
              if(value.length < 5){
                return '昵称至少得5个字符啊';
              }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,repass: function(value){
                if($('#L_pass').val()!=$('#L_repass').val()){
                    return '两次密码不一致';
                }
            }
          });

          //监听提交
          form.on('submit(add)', function(data){

            var id = document.getElementById("id").value;
            console.log(id);
            if (id === null || id === '') {
                layer.alert("增加成功", {icon: 6},function () {
            	document.getElementById('deptForm').submit();
                // 获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
            });
            } else{
                layer.alert("修改成功", {icon: 6},function () {
            	document.getElementById('deptForm').submit();
                // 获得frame索引
                var index = parent.layer.getFrameIndex(window.name);
                //关闭当前frame
                parent.layer.close(index);
               
            });
            }
            console.log(data);
            //发异步，把数据提交给php
            return false;
          });
        });
    </script>


    <script type="application/javascript">
        var layData = [ 'form' ];
        layui.use(layData, function() {
            var form = layui.form;
            form.on('select(dept)', function(data){
                // initChildAreas(data.value, 'city');
                 console.log("1234")
                // console.log( document.getElementById("deptId").value);
                $.ajax({
                    type : "GET",
                    url : "/recruit/joblist",
                    data: {
                        id : document.getElementById("deptId").value
                    },
                    success :function(data) {
                        console.log(data);
                        console.log(data.length);
                        if (true) {
                            $("#jobId").find("option").remove();
                            form.render();
                        }
                        for(var i= 0; i< data.length;i++) {
                            $("#jobId").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                            form.render();
                            // console.log(data.length)
                            console.log(data[i].name +  "--"  + data[i].id);
                        }
                    }
                })
                form.render();
                console.log(document.getElementById("jobId"))
            });
        });
    </script>
    
  </body>

</html>