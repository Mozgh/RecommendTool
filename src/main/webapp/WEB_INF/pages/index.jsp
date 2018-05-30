<%--
  Created by IntelliJ IDEA.
  User: feir4
  Date: 2018/5/12
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>首页</title>
    <meta charset="utf-8"/>
    <meta code="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/Recommend/statics/css/amazeui.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/core.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/menu.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/index.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/admin.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/page/typography.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/page/form.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/component.css"/>
</head>
<body>
<div class="admin">
    <c:import url="sidebar_left.jsp"/>
    <div class="am-u-md-3">
        <div class="card-box">
            <h4 class="header-title m-t-0 m-b-30">推荐任务</h4>
            <div class="widget-chart-1 am-cf">
                <div id="widget-chart-box-1" style="height: 110px;width: 110px;float: left;">
                </div>

                <div class="widget-detail-1" style="float: right;">
                    <h2 class="p-t-10 m-b-0"> 256 </h2>
                    <p class="text-muted">今日收入</p>
                </div>
            </div>
        </div>
    </div>
</div>
<a href="admin-offcanvas" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
   data-am-offcanvas="{target: '#admin-offcanvas'}"><!--<i class="fa fa-bars" aria-hidden="true"></i>--></a>

<script type="text/javascript" src="/Recommend/statics/js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="/Recommend/statics/js/amazeui.min.js"></script>
<script type="text/javascript" src="/Recommend/statics/js/app.js"></script>
<script type="text/javascript" src="/Recommend/statics/js/blockUI.js"></script>
<script type="text/javascript" src="/Recommend/statics/js/charts/echarts.min.js"></script>
<script type="text/javascript" src="/Recommend/statics/js/charts/indexChart.js"></script>
</body>
</html>
