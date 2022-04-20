<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  
  <head>
    <meta charset="UTF-8">
    <title>薪资信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="shortcut icon" href="${ctx}/public/logo.ico" type="image/x-icon" />
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
    <div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a>
          <cite>薪资信息</cite></a>
      </span>
      <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="${ctx }/employee/payList?pageNum=1&pageSize=6" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row" style="" align="center">
        <form class="layui-form layui-col-md12 x-so" method="get" action="${ctx }/employee/paydetail">
          <!-- <input class="layui-input" placeholder="开始日" name="start" id="start">
          <input class="layui-input" placeholder="截止日" name="end" id="end"> -->
          <input type="text" name="content" id="content" style="width:50%;"  placeholder="请输入时间" autocomplete="off" class="layui-input">
          <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon">&#xe615;</i></button>
          <input type="hidden" name="pageNum" value="1"/>
          <input type="hidden" name="pageSize" value="6"/>
        </form>
      </div>
<%--       <xblock>--%>
<%-- <!--        <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button> -->--%>
<%--        <button class="layui-btn" onclick="x_admin_show('加班申请','${ctx}/employee/overtimeadd')"><i class="layui-icon"></i>加班申请</button>--%>
<%--        <span class="x-right" style="line-height:40px">共有数据：88 条</span>--%>
<%--      </xblock>--%>
     
      
      <table class="layui-table">
        <thead>
          <tr>
<%--            <th>--%>
<%--              <div class="layui-unselect header layui-form-checkbox" lay-skin="primary"><i class="layui-icon">&#xe605;</i></div>--%>
<%--            </th>--%>
            <th>姓名</th>
           <th>薪资日期</th>
           <th>基础薪资</th>
         <th>加班薪资</th>
         <th>请假扣除</th>
         <th>总额</th>
         <th>创建时间</th>
         <!-- <th>状态</th> -->
        </thead>
        <tbody>
        <c:forEach items="${requestScope.paylist}" var="dept" varStatus="stat" >
     <tr>
<%--            <td>--%>
<%--              <div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id='2'><i class="layui-icon">&#xe605;</i></div>--%>
<%--            </td>--%>
            <td>${dept.name }</td>
            <td>${dept.paytime }</td>
            <td>${dept.basicpay }</td>
            <td>${dept.overtimepay }</td>
            <td>${dept.hoildaypay }</td>
            <td>${dept.paysum }</td>
            <td>${dept.createtime }</td>

           <!--  <td class="td-status">
              <span class="layui-btn layui-btn-normal layui-btn-mini">已启用</span></td> -->
<%--                <td class="td-manage">--%>
<%--                <c:if test="${dept.state == 'submitted'} ">--%>
<%--                  <a title="重新提交"  href="${ctx}/employee/updatehour?id=${dept.id }">--%>
<%--                    <i class="layui-icon">&#xe642;</i>--%>
<%--                  </a>--%>
<%--                </c:if>--%>
<%--                </td>--%>
              </tr>
        </c:forEach>
        
          
          
          
        </tbody>
      </table>
      <div class="page">
        <!-- <div>
          <a class="prev" href="">&lt;&lt;</a>
          <a class="num" href="">1</a>
          <span class="current">2</span>
          <a class="num" href="">3</a>
          <a class="num" href="">489</a>
          <a class="next" href="">&gt;&gt;</a>
        </div> -->
        <div>
          <a class="prev" href="">&lt;&lt;</a>
          <c:if test="${pageInfo.pageNum-1 != 0 }">
                <a class="num" href="../employee/overtimeList?pageNum=${pageInfo.pageNum-1}&pageSize=6">${pageInfo.pageNum - 1}</a>
            </c:if>
          <span class="current">${pageInfo.pageNum}</span>
          <c:if test="${pageInfo.pageNum + 1  <= pageInfo.pages }">
                <a class="num" href="../employee/overtimeList?pageNum=${pageInfo.pageNum+1}&pageSize=6">${pageInfo.pageNum + 1}</a>
            </c:if>
          <!-- <a class="num" href="">489</a> -->
          <a class="next" href="">&gt;&gt;</a>
        </div>
      </div>

    </div>
    <script>
        layui.use('laydate', function(){
            var laydate = layui.laydate;
        //执行一个laydate实例
            laydate.render({
            elem: '#content' //指定元素
            ,type: 'month'
            });
      });

    </script>
    <script>var _hmt = _hmt || []; (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
      })();</script>
  </body>

</html>