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
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/pagination/src/page.css">
<div id="article_list">

    <table>
        <c:forEach items="${articleList}" var="article">
            <tr>
                <td align="center" style="width: 20px">
                    <img style="width: 16px;height: 17px;" src="${pageContext.request.contextPath}/static/icons/article_list.png">
                </td>
                <td style="width: 400px">
                    <a id="article_list_a" href="${pageContext.request.contextPath}/article/detail/${article.id}">${article.title}</a>
                </td>
                <td align="center" width="120px">
                    <span id="article_list_date_span"><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/></span>
                </td>
                <td align="center" width="120px">
                    <span id="article_list_btn">
                        <a href="${pageContext.request.contextPath}/article/edit?id=${article.id}" id="update_btn">修改</a>
                        <span style="margin: 0 5px;"></span>
                        <a href="#" id="delete_btn" onclick="checkDelete('${article.id}')">删除</a>
                    </span>
                </td>
            </tr>
            <tr style="height: 13px">
                <td colspan="4">
                    <p style="height: 0;border-bottom: 1px #404000 solid"></p>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div id="pagination"></div>

</div>

<script src="${pageContext.request.contextPath}/static/pagination/src/pagination.js"></script>
<script>
    page({
        box:'pagination',//存放分页的容器
        href:'?page=',//跳转连接
        page:${page},//当前页码
        count:${count},//总页数
        num:5,//页面展示的页码个数
    });

    function checkDelete(id) {
        layer.confirm('确定要删除吗？', {
            btn: ['确定','取消'], //按钮
            icon:3,
            closeBtn: 0,
            offset: '200px',
            title:false
        }, function(){
            $(location).prop('href', '${pageContext.request.contextPath}/article/delete/'+id);
        }, function(){
            layer.msg('取消删除!',{
                offset: '200px'
            });
        });
    }
</script>