<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: feir4
  Date: 2018/5/29
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新建任务</title>
    <meta charset="utf-8"/>
    <meta code="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/Recommend/statics/css/amazeui.css"/>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/Recommend/statics/css/core.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/menu.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/index.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/admin.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/page/typography.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/page/form.css"/>
</head>
<body>
<header class="am-topbar am-topbar-fixed-top">
    <div class="am-topbar-left am-hide-sm-only">
        <a href="index.html" class="logo"><span>Admin<span>to</span></span><i class="zmdi zmdi-layers"></i></a>
    </div>

    <div class="contain">
        <ul class="am-nav am-navbar-nav am-navbar-left">

            <li><h4 class="page-title">新建任务</h4></li>
        </ul>

        <ul class="am-nav am-navbar-nav am-navbar-right">
            <li class="inform"><i class="am-icon-bell-o" aria-hidden="true"></i></li>
            <li class="hidden-xs am-hide-sm-only">
                <form role="search" class="app-search">
                    <input type="text" placeholder="Search..." class="form-control">
                    <a href=""><img src="/Recommend/statics/img/search.png"></a>
                </form>
            </li>
        </ul>
    </div>
</header>

<div class="admin">

    <c:import url="sidebar_left.jsp"/>

    <div class="content-page">
        <!-- Start content -->
        <div class="content">
            <div class="am-g">
                <!-- Row start -->
                <div class="am-u-sm-12">
                    <div class="card-box">
                        <ul class="am-nav am-fr">
                            <li class="am-dropdown" data-am-dropdown>
                                <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                                    <span class="am-icon-caret-down"></span>
                                </a>
                                <ul class="am-dropdown-content">
                                    <li>1231234</li>
                                </ul>
                            </li>
                        </ul>

                        <%--<form action="/task/createTask" method="post" class="am-form" data-am-validator>--%>
                        <div class="am-form">
                            <fieldset>
                                <legend>任务信息</legend>
                                <div class="am-form-group">
                                    <label for="taskName">任务名称</label>
                                    <input type="text" id="taskName" minlength="3" placeholder="输入任务名称"
                                           required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="method">算法</label>
                                    <select id="method" required>
                                        <option value="">-=请选择一项=-</option>
                                        <option value="1">K-means聚类算法</option>
                                    </select>
                                    <span class="am-form-caret"></span>
                                </div>

                                <div class="am-form-group">
                                    <label for="description">任务描述</label>
                                    <input type="text" id="description" minlength="1" placeholder="输入任务描述" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="centerCount">初始聚类个数</label>
                                    <input type="number" id="centerCount" placeholder="输入数字" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="dbHost">数据库地址</label>
                                    <input type="text" id="dbHost" minlength="1" placeholder="输入IP地址" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="dbPort">数据库端口</label>
                                    <input type="number" id="dbPort" minlength="1" placeholder="输入端口" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="dbName">数据库名</label>
                                    <input type="text" id="dbName" minlength="1" placeholder="用户名" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="dbUser">数据库用户</label>
                                    <input type="text" id="dbUser" minlength="1" placeholder="用户名" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="dbPassword">数据库密码</label>
                                    <input type="password" id="dbPassword" minlength="1" placeholder="输入密码" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="querySql">数据查询语句</label>
                                    <input type="text" id="querySql" minlength="1" placeholder="sql" required/>
                                </div>

                                <button class="am-btn am-btn-secondary" type="submit" onclick="submit()">提交</button>
                            </fieldset>
                        <%--</form>--%>
                        </div>

                    </div>
                </div>
                <!-- Row end -->
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

<script type="text/javascript">
    function submit() {
        var taskName = document.getElementById("taskName");
        var method = document.getElementById("method");
        var description = document.getElementById("description");
        var centerCount = document.getElementById("centerCount");
        var dbHost = document.getElementById("dbHost");
        var dbPort = document.getElementById("dbPort");
        var dbName = document.getElementById("dbName");
        var dbUser = document.getElementById("dbUser");
        var dbPassword = document.getElementById("dbPassword");
        var querySql = document.getElementById("querySql");

        data = {
            taskName: taskName.value,
            method: method.value,
            description: description.value,
            centerCount: centerCount.value,
            dbHost: dbHost.value,
            dbPort: dbPort.value,
            dbName: dbName.value,
            dbUser: dbUser.value,
            dbPassword: dbPassword.value,
            querySql: querySql.value
        }
        console.log(data.toString());

        var url = "http://localhost:8080/task/createTask";
        $.post(url, data, function (responseTxt) {
            alert(responseTxt.message);
        })
    }


</script>
</body>
</html>
