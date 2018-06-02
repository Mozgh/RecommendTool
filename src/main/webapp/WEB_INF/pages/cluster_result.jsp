<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: feir4
  Date: 2018/6/1
  Time: 21:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>推荐结果</title>
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
            <div class="am-g">
                <%--<div class="am-u-md-6" >--%>
                <!-- 折线图堆叠 -->
                <div class="card-box">
                    <div id="columnar1" style="width: 100%;height: 400px;"></div>
                </div>
                <%--</div>--%>
                <%--</div>--%>
            </div>

            <div class="am-g">
                <div class="am-u-md-4">
                    <div  class="card-box">
                        <h4 class="header-title m-t-0 m-b-30">推荐结果</h4>
                        <div class="am-g">
                            <%--<div class="am-u-sm-12 am-u-md-6">
                                <div class="am-btn-toolbar">
                                </div>
                            </div>--%>
                            <div class="am-u-sm-12 am-u-md-3">
                                <div class="am-input-group am-input-group-sm">
                                    <input type="text" id="entityCode">
                                    <span class="am-input-group-btn">
				            <button class="am-btn am-btn-default" type="button"
                                    onclick="queryRecommendResult()">查询</button>
				          </span>
                                </div>
                            </div>
                        </div>
                        <div id="recommend-dev" class="inbox-widget nicescroll" style="height: 450px; overflow: hidden; outline: none;"
                             tabindex="5000">
                        </div>
                    </div>
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
<script type="text/javascript">

    function queryClusterResult() {
        var url = "http://localhost:8080/task/clusterResult";
        data = {
            taskId: 1001
        };
        $.get(url, data, function (responseTxt) {
            console.log(responseTxt.message);
            renderColumnar(responseTxt);
        })
    }

    window.onload = function () {
        queryClusterResult();
    }

    function renderColumnar(data) {
        var columnar1 = echarts.init(document.getElementById("columnar1"));

        var xAxisList = new Array();
        var seriesList = new Array();
        for (var i = 0; i < data.data.length; i++) {
            xAxisList.push(data.data[i].mongodbId);
            seriesList.push(data.data[i].count);
        }

        option = {

            title: {
                text: "聚类结果",
                x: 'left'
            },

            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: xAxisList,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: 'entityCount',
                    type: 'bar',
                    barWidth: '30%',
                    data: seriesList
                }
            ]
        };

        columnar1.setOption(option);
    }

    function queryClusterResult() {
        var url = "http://localhost:8080/task/clusterResult";
        data = {
            taskId: 1001
        };
        $.get(url, data, function (responseTxt) {
            console.log(responseTxt.message);
            renderColumnar(responseTxt);
        })
    }

    function queryRecommendResult() {
        var url = "http://localhost:8080/task/recommendResult";
        var code = document.getElementById("entityCode");
        data = {
            code: code.value
        };
        var result;
        $.get(url, data, function (responseTxt) {
            console.log(responseTxt.data)
            result = responseTxt.data;
            var cardBox = document.getElementById("recommend-dev");
            for (var i = 0; i < result.length; i++) {
                var item = document.createElement("div");
                item.setAttribute("class", "inbox-item");
                var p = document.createElement("p");
                p.setAttribute("class", "inbox-item-author");
                p.appendChild(document.createTextNode(result[i]));
                item.appendChild(p);
                cardBox.appendChild(item);
            }
        });
    }

    function renderRecommendList() {
        queryRecommendResult();
    }

    window.onload = function () {
        queryClusterResult();
    }
</script>
</body>
</html>
