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
                                    <li>1231234</li>
                                    <li>1231234</li>
                                    <li>1231234</li>
                                </ul>
                            </li>
                        </ul>

                        <form action="" class="am-form" data-am-validator>
                            <fieldset>
                                <legend>JS 表单验证</legend>
                                <div class="am-form-group">
                                    <label for="doc-vld-code-2">用户名：</label>
                                    <input type="text" id="doc-vld-code-2" minlength="3" placeholder="输入用户名（至少 3 个字符）"
                                           required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="doc-vld-email-2">邮箱：</label>
                                    <input type="email" id="doc-vld-email-2" placeholder="输入邮箱" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="doc-vld-url-2">网址：</label>
                                    <input type="url" id="doc-vld-url-2" placeholder="输入网址" required/>
                                </div>

                                <div class="am-form-group">
                                    <label for="doc-vld-age-2">年龄：</label>
                                    <input type="number" class="" id="doc-vld-age-2" placeholder="输入年龄  18-120" min="18"
                                           max="120" required/>
                                </div>

                                <div class="am-form-group">
                                    <label class="am-form-label">爱好：</label>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox" value="橘子" code="docVlCb" minchecked="2" maxchecked="4"
                                               required> 橘子
                                    </label>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox" value="苹果" code="docVlCb"> 苹果
                                    </label>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox" value="菠萝" code="docVlCb"> 菠萝
                                    </label>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox" value="芒果" code="docVlCb"> 芒果
                                    </label>
                                    <label class="am-checkbox-inline">
                                        <input type="checkbox" value="香蕉" code="docVlCb"> 香蕉
                                    </label>
                                </div>

                                <div class="am-form-group">
                                    <label>性别： </label>
                                    <label class="am-radio-inline">
                                        <input type="radio" value="" code="docVlGender" required> 男
                                    </label>
                                    <label class="am-radio-inline">
                                        <input type="radio" code="docVlGender"> 女
                                    </label>
                                    <label class="am-radio-inline">
                                        <input type="radio" code="docVlGender"> 其他
                                    </label>
                                </div>

                                <div class="am-form-group">
                                    <label for="doc-select-1">下拉单选框</label>
                                    <select id="doc-select-1" required>
                                        <option value="">-=请选择一项=-</option>
                                        <option value="option1">选项一...</option>
                                        <option value="option2">选项二.....</option>
                                        <option value="option3">选项三........</option>
                                    </select>
                                    <span class="am-form-caret"></span>
                                </div>

                                <div class="am-form-group">
                                    <label for="doc-select-2">多选框</label>
                                    <select multiple class="" id="doc-select-2" minchecked="2" maxchecked="4">
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                    </select>
                                </div>

                                <div class="am-form-group">
                                    <label for="doc-vld-ta-2">评论：</label>
                                    <textarea id="doc-vld-ta-2" minlength="10" maxlength="100"></textarea>
                                </div>

                                <button class="am-btn am-btn-secondary" type="submit">提交</button>
                            </fieldset>
                        </form>


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
</body>
</html>
