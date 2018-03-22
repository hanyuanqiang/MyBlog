<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/27
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>挪威司机</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/highlight/styles/docco.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/template/template.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/my.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/layer/mobile/need/layer.css">

    <script src="${pageContext.request.contextPath}/static/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/highlight/highlight.pack.js"></script>
    <script src="${pageContext.request.contextPath}/static/layer/layer.js"></script>
</head>
<!-- high margin = thin layout -->
<body>

<div class="t_container" style="width:1200px;margin: 0 auto">

    <div class="header">
        <div style="height: 100%;padding-top: 10px;margin: 0px;">
            <p style="float: right;margin: 0px 20px;height: 25px">
                <span id="currentTime" style="font-size: 16px"></span>
            </p>
            <p style="margin: 0px 130px;">
                <span></span>
                <span class="head_nickName">${currentUser.nickName}</span>
            </p>
            <p style="margin-left:400px;margin-top: 0px;">
               <span class="head_en">
                    Keep track of what I really think.<br>
                    Hope that many years later, we still have the courage to remember!
               </span>
            </p>
        </div>
    </div>

    <div class="stripes"></div>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/article/list">文章列表</a>
        <a href="${pageContext.request.contextPath}/article/edit">新文章</a>
        <a href="${pageContext.request.contextPath}/type/list">类型管理</a>
        <a href="${pageContext.request.contextPath}/user/center">个人中心</a>
        <a href="${pageContext.request.contextPath}/github/gen">一键生成github页面</a>
        <a href="${pageContext.request.contextPath}/user/logout">注销</a>
        <div class="clearer"><span></span></div>
    </div>

    <div class="stripes"></div>

    <div class="main">
        <div class="left">
            <div class="content">
                <jsp:include page="${subPage}"/>
            </div>
        </div>

        <div class="right">
            <div class="subnav">
                <h3 align="center">个人简介</h3>
                <p align="center">
                    <img src="${currentUser.avatar}" style="width: 100px;height: 100px;border-radius: 8px">
                </p>
                <p><span style="margin-right: 10px;font-size: 16px;color: yellow">名称:</span><span style="color: #f1fdff;font-size: 18px;font-family: 楷体">${currentUser.nickName}</span></p>
                <p><span style="margin-right: 10px;font-size: 16px;color: yellow">签名:</span><span style="color: #f1fdff;font-size: 18px;font-family: 楷体">${currentUser.signature}</span></p>
                <p><span style="margin-right: 10px;font-size: 16px;color: yellow;">介绍:</span><span style="color: #f1fdff;font-size: 18px;font-family: 楷体">${currentUser.introduce}</span></p>

                <p style="height: 0;border-bottom: 1px #f9fbfd solid;margin: 5px 0px"></p>

                <h3>文章分类</h3>
                <ul>
                    <c:forEach items="${typeList}" var="type">
                        <li class="main_type_list_li">
                            <a class="main_type_list_a" href="${pageContext.request.contextPath}/article/list?type.id=${type.id}">
                                <span><img src="${pageContext.request.contextPath}/static/icons/type_list.png"></span>
                                <span>${type.typeName}</span>
                                <span style="margin-left: 5px;color: #9f9f9f;">(${type.articleNum})</span>
                            </a>
                        </li>
                    </c:forEach>
                </ul>

                <p style="height: 0;border-bottom: 1px #f9fbfd solid;margin: 5px 0px"></p>

                <h3>时间归档</h3>
                <ul>
                    <c:forEach items="${classifyByMonth}" var="map">
                        <li class="main_type_list_li">
                            <a class="main_type_list_a" href="${pageContext.request.contextPath}/article/list?createTime=${map.key}">
                                <span><img src="${pageContext.request.contextPath}/static/icons/filing_list.png"></span>
                                <span><c:out value="${map.key}"/></span>
                                <span style="margin-left: 5px;color: #9f9f9f;">(<c:out value="${map.value}"/>)</span>
                            </a>
                        </li>
                    </c:forEach>
                </ul>

                <p style="height: 0;border-bottom: 1px #f9fbfd solid;margin: 5px 0px"></p>

                <h3>文章权限</h3>
                <ul>
                    <li class="main_type_list_li">
                        <a class="main_type_list_a" href="${pageContext.request.contextPath}/article/list?visitAuth=所有人可见">
                            <span><img src="${pageContext.request.contextPath}/static/icons/type_list.png"></span>
                            <span>所有人可见</span>
                            <span style="margin-left: 5px;color: #9f9f9f;"></span>
                        </a>
                    </li>
                    <li class="main_type_list_li">
                        <a class="main_type_list_a" href="${pageContext.request.contextPath}/article/list?visitAuth=仅自己可见">
                            <span><img src="${pageContext.request.contextPath}/static/icons/type_list.png"></span>
                            <span>仅自己可见</span>
                            <span style="margin-left: 5px;color: #9f9f9f;"></span>
                        </a>
                    </li>
                </ul>

            </div>
        </div>

        <div class="clearer"><span></span></div>
    </div>

    <div class="footer" style="padding-top: 20px">
        <div align="center" style="margin-top: 0px">
            <span id="copyright">Copyright©2018 ${currentUser.nickName}</span>
        </div>
    </div>
</div>

<script type="text/javascript">
    hljs.initHighlightingOnLoad();
    $("code").attr("id","codeId");
    $("pre").attr("id","preId");

    setDate();
    function setDate() {
        var mydate = new Date();
        var str = "";
        str += (mydate.getMonth() + 1) + "月";
        str += mydate.getDate() + "日 ";
        var dday = mydate.getDay();
        var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
        str += weekday[dday];
        $("#currentTime").text(str);
    };

</script>

</body>

</html>