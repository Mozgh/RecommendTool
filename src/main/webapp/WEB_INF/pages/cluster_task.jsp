<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: feir4
  Date: 2018/5/29
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>推荐任务</title>
    <meta charset="utf-8" />
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
            <div class="card-box">
                <!-- Row start -->
                <div class="am-g">
                    <div class="am-u-sm-12 am-u-md-6">
                        <div class="am-btn-toolbar">
                            <div class="am-btn-group am-btn-group-xs">
                                <button type="button" class="am-btn am-btn-default" ><span class="am-icon-plus"></span> 新增</button>
                                <button type="button" class="am-btn am-btn-default" onclick="openTask()"><span class="am-icon-plus"></span> 打开</button>
                                <button type="button" class="am-btn am-btn-default" onclick="closeTask()"><span class="am-icon-plus"></span> 关闭</button>
                                <button type="button" class="am-btn am-btn-default" onclick="deleteTask()"><span class="am-icon-plus"></span> 删除</button>
                            </div>
                        </div>
                    </div>

                    <div class="am-u-sm-12 am-u-md-3">
                        <div class="am-input-group am-input-group-sm">
                            <input type="text" class="am-form-field">
                            <span class="am-input-group-btn">
				            <button class="am-btn am-btn-default" type="button" onclick="queryTaskList()">搜索</button>
				          </span>
                        </div>
                    </div>
                </div>
                <!-- Row end -->

                <!-- Row start -->
                <div class="am-g">
                    <div class="am-u-sm-12">
                        <form class="am-form">
                            <table class="am-table am-table-striped am-table-hover table-main">
                                <thead>
                                <tr>
                                    <th class="table-check"><input type="checkbox" /></th>
                                    <th class="table-id">ID</th>
                                    <th class="table-title">任务名</th>
                                    <th class="table-type">使用算法</th>

                                    <%--<th class="table-set">操作</th>--%>
                                </tr>
                                </thead>
                            </table>
                            <hr />
                            <%--<p>注：.....</p>--%>
                        </form>
                    </div>
                </div>
                <!-- Row end -->
            </div>

        </div>
    </div>
</div>


<!-- navbar -->
<a href="admin-offcanvas" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"><!--<i class="fa fa-bars" aria-hidden="true"></i>--></a>

<script type="text/javascript" src="/Recommend/statics/js/jquery-2.1.0.js"></script>
<script type="text/javascript" src="/Recommend/statics/js/amazeui.min.js"></script>
<script type="text/javascript" src="/Recommend/statics/js/app.js"></script>
<script type="text/javascript" src="/Recommend/statics/js/blockUI.js"></script>

<div id="taskOper" class="am-btn-toolbar">
    <div  class="am-btn-group am-btn-group-xs">
        <button name="editButton" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span> 编辑</button>
        <button name="openButton" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="openTask()"><span class="am-icon-pencil-square-o"></span> 打开</button>
        <button name="closeButton" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span> 关闭</button>
        <button name="deleteButton" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span> 删除</button>
    </div>
</div>

<script type="text/javascript" >

    function renderTd(task) {
        var my_table = document.getElementsByTagName("table")[0];
        tBody = document.createElement("tbody");
        for (var i = 0; i < task.length; i ++) {
            var newTr = document.createElement("tr");
            var id = document.createElement("td");
            id.appendChild(document.createTextNode(task[i].id))
            var title = document.createElement("td");
            title.appendChild(document.createTextNode(task[i].name));
            var method = document.createElement("td");
            method.appendChild(document.createTextNode(task[i].method));
            var input = document.createElement("td");
            var inputTag = document.createElement("input");
            inputTag.setAttribute("type", "checkbox");
            inputTag.setAttribute("value", task[i].id);
            inputTag.setAttribute("name", "checkTaskId");
            input.appendChild(inputTag);

            newTr.appendChild(input);
            newTr.appendChild(id);
            newTr.appendChild(title);
            newTr.appendChild(method);
//            newTr.appendChild(oper);
            tBody.appendChild(newTr);
        }
        my_table.appendChild(tBody);
        return;
    }

    function queryTaskList() {
        var url = "http://localhost:8080/task/queryTaskList.do";
        $.get(url, "", function (responseTxt, statusTxt, xhr) {
            var taskList = responseTxt.data;
            renderTd(taskList);
        });
    }

    window.onload = function () {
        this.queryTaskList();
    };
    
    function getTaskIdList() {
        var checkbox = document.getElementsByName("checkTaskId");
        var taskIdList = new Array();
        for (var i = 0; i< checkbox.length; i++) {
            if (checkbox[i].checked) {
//                taskIdList[i] = checkbox[i].getAttribute("value");
                taskIdList.push(checkbox[i].getAttribute("value"));
            }
        }
        return taskIdList;
    }

    function openTask() {
        var url = "http://localhost:8080/task/openTask";
        data = {
            taskId : getTaskIdList()
        };
        $.post(url, data, function (responseTxt) {
            alert(responseTxt.message);
        })

    }

    function closeTask() {
        var url = "http://localhost:8080/task/closeTask";
        data = {
            taskId : getTaskIdList()
        };
        $.post(url, data, function (responseTxt) {
            alert(responseTxt.message);
        })
    }

    function deleteTask() {
        var url = "http://localhost:8080/task/deleteTask";
        data = {
            taskId : getTaskIdList()
        };
        $.post(url, data, function (responseTxt) {
            alert(responseTxt.message);
        })
    }

</script>
</body>
</html>
