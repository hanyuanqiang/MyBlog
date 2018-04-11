<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/8
  Time: 2:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div id="article_detail">
    <p id="article_detail_title" align="center">${article.title}</p>
    <p align="center" style="margin-bottom: 5px;">
        <span class="article_detail_time_type">
            <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </span>
        <span style="margin: 0 10px;"></span>
        <span class="article_detail_time_type">${article.source}</span>
        <span style="margin: 0 10px;"></span>
        <span class="article_detail_time_type">分类：${article.type.typeName}</span>
    </p>
    <div class="common_line"></div>
    <p>${article.content}</p>
    <p style="height: 0;border-bottom: 1px #707172 solid;margin: 5px 0px"></p>
    <p>
        关键词：<span style="font-size: 18px;font-family: 楷体;color: yellow;">${article.keywords}</span>
        <span style="width:20px">&nbsp;</span>
        最后更新：
        <span style="font-size: 15px;color: yellow;">
            <fmt:formatDate value="${article.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </span>
    </p>
</div>