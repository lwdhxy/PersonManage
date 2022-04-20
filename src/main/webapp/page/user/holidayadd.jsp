<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>请假审批</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${ctx}/public/css/font.css">
    <link rel="stylesheet" href="${ctx}/public/css/xadmin.css">
    <link rel="stylesheet" href="${ctx}/public/laydate/laydate.js">
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
        <form class="layui-form" method="POST" id="deptForm"  action="${ctx}/user/holidayupdate">
            <input type="hidden" id="sort" name="sort" value="qingjia">
          <div class="layui-form-item" >
              <label for="begintime" class="layui-form-label">
                  <span class="x-red">*</span>请假日期
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="begintime" name="begintime" lay-verify="required"
                  class="layui-input" value="${dept.begintime }" disabled>
              </div>
          </div>
            <div class="layui-form-item" >
              <label for="duration" class="layui-form-label">
                  <span class="x-red">*</span>请假时长
              </label>
              <div class="layui-input-inline">
                  <input type="number" id="duration" name="duration" required="" lay-verify="required"
                  autocomplete="off" class="layui-input" value="${dept.duration }" disabled>
              </div>
          </div>
            <div class="layui-form-item" >
              <label for="cause" class="layui-form-label">
                  <span class="x-red">*</span>原因
              </label>
              <div class="layui-input-inline">
                  <textarea rows="30" cols="20" type="" id="cause" name="cause" required="" lay-verify="required"
                            autocomplete="off" class="layui-input" >${dept.cause }</textarea>
              </div>
          </div>

          <div class="layui-form-item">
              <td class="td-manage">
                  <a title="通过"  href="${ctx}/user/holidayupdate?id=${dept.id }&state=approve" class="layui-btn layui-btn-primary layui-btn-xs">
                      通过
                  </a>
                  <a title="驳回" href="${ctx}/user/holidayupdate?id=${dept.id }&state=reject" class="layui-btn layui-btn-primary layui-btn-xs">
                      驳回
              </a>
              </td>
          </div>
      </form>
    </div>
    <script>
        layui.use('laydate', function(){
            var laydate = layui.laydate;

            //执行一个laydate实例
            laydate.render({
                elem: '#begintime' //指定元素
                ,type: 'date'
            });
        });
        layui.use(['form','layer'], function() {
            $ = layui.jquery;
            var form = layui.form
                , layer = layui.layer;

            //监听提交
            form.on('submit(add)', function (data) {
                //自定义验证规则
                form.verify({
                    worknumber: function (value) {
                        console.log(value);
                        if (value > 40) {
                            return '工时不能大于40个小时';
                        }
                    }
                });
            });
        });
    </script>
    
  </body>

</html>