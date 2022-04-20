<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>人事信息管理系统</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="shortcut icon" href="${ctx}/public/logo.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${ctx}/public/css/font.css">
	<link rel="stylesheet" href="${ctx}/public/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="${ctx}/public/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/public/js/xadmin.js"></script>

</head>
<body>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo"><a href="./employee/index.html">人事管理系统</a></div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe699;</i>
        </div>
        
        <ul class="layui-nav right" lay-filter="">
          <li class="layui-nav-item">
            <a href="javascript:;">${employee_session.name}</a>
            <dl class="layui-nav-child"> <!-- 二级菜单 -->
              <dd><a href="./employee/logout?username=${employee_session.name}">退出</a></dd>
            </dl>
          </li>
        </ul>
        
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
     <!-- 左侧菜单开始 -->
    <div class="left-nav">
      <div id="side-nav">
        <ul id="nav">
           
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe726;</i>
                    <cite>个人中心</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${ctx }/employee/employeedetails?id=${employee_session.id}">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>个人信息</cite>
                        </a>
                    </li >

                </ul>
            </li>

            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6ce;</i>
                    <cite>个人工时</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${ctx }/employee/hourList?pageNum=1&pageSize=6">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>查看工时</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${ctx }/employee/hour">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>工时填报</cite>
                        </a>
                    </li >
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6ce;</i>
                    <cite>请假管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${ctx }/employee/holidayList?pageNum=1&pageSize=6">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>查看请假信息</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${ctx }/employee/holidayadd?sort=qingjia">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>请假申请</cite>
                        </a>
                    </li >
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6ce;</i>
                    <cite>加班管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${ctx }/employee/overtimeList?pageNum=1&pageSize=6">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>查看加班信息</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${ctx }/employee/holidayadd">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>加班申请</cite>
                        </a>
                    </li >
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6ce;</i>
                    <cite>薪资管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${ctx }/employee/paydetail?pageNum=1&pageSize=6">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>查看薪资信息</cite>
                        </a>
                    </li >
                </ul>
            </li>

            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6ce;</i>
                    <cite>公告管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${ctx }/notice/list?pageNum=1&pageSize=6">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>公告查询</cite>
                        </a>
                    </li >
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6b4;</i>
                    <cite>下载中心</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${ctx }/document/list?pageNum=1&pageSize=6">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>文档查询</cite>
                        </a>
                    </li>

                </ul>
            </li>

<%--            <li>--%>
<%--                <a href="javascript:;">--%>
<%--                    <i class="iconfont">&#xe6b4;</i>--%>
<%--                    <cite>聊天室</cite>--%>
<%--                    <i class="iconfont nav_right">&#xe697;</i>--%>
<%--                </a>--%>
<%--                <ul class="sub-menu">--%>
<%--                    <li>--%>
<%--                        <a _href="${ctx }/chat/chat">--%>
<%--                            <i class="iconfont">&#xe6a7;</i>--%>
<%--                            <cite>聊天</cite>--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </li>--%>
        </ul>
      </div>
    </div>
    <!-- <div class="x-slide_left"></div> -->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
          <ul class="layui-tab-title">
            <li class="home"><i class="layui-icon">&#xe68e;</i>我的桌面</li>
          </ul>
          <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='${ctx}/employee/welcome' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
          </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 中部结束 -->
    <!-- 底部开始 -->
    <div class="footer">
        <div class="copyright">@PANZHIHUA University  </div>
    </div>
    <!-- 底部结束 -->
 
</body>
</html>