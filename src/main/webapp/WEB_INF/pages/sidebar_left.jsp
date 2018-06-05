<%--
  Created by IntelliJ IDEA.
  User: feir4
  Date: 2018/5/13
  Time: 9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- sidebar start -->
<head>
    <meta charset="utf-8"/>
    <meta code="viewport" content="width=device-width, initial-scale=1.0">
    <title>后台模板</title>
    <link rel="stylesheet" href="/Recommend/statics/css/amazeui.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/core.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/menu.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/index.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/admin.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/page/typography.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/page/form.css"/>
    <link rel="stylesheet" href="/Recommend/statics/css/component.css"/>
</head>
    <div class="admin-sidebar am-offcanvas  am-padding-0" id="admin-offcanvas">
        <div class="am-offcanvas-bar admin-offcanvas-bar">
            <!-- User -->
            <div class="user-box am-hide-sm-only">
                <h5><a href="#">Mat Helme</a></h5>
                <h6>zhanggh430@163.com</h6>
                <ul class="list-inline">
                    <li>
                        <a href="#">
                            <i class="fa fa-cog" aria-hidden="true"></i>
                        </a>
                    </li>

                    <li>
                        <a href="#" class="text-custom">
                            <i class="fa fa-cog" aria-hidden="true"></i>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- End User -->

            <ul class="am-list admin-sidebar-list">
                <li><a href="index.jsp"><span class="am-icon-home"></span> 首页</a></li>
                <li></li>
                <%--<li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#collapse-nav1'}"><span class="am-icon-table"></span>
                        任务 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
                    <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav1">
                        <li><a href="cluster_task.jsp" class="am-cf"> 任务列表</a></li>
                        <li><a href="cluster_task_add.jsp">新建任务</a></li>
                    </ul>
                </li>--%>
                <li class="admin-parent">
                    <a class="am-cf" data-am-collapse="{target: '#collapse-nav2'}"><i class="am-icon-line-chart"
                                                                                      aria-hidden="true"></i> 统计图表 <span
                            class="am-icon-angle-right am-fr am-margin-right"></span></a>
                    <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav2">
                        <li><a href="cluster_result.jsp" class="am-cf"> 数据查看</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
<!-- sidebar end -->
