<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/9
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<style>
    #typeName:focus {
        outline:none;
        border: 2px solid red;
    }
</style>

<div>
    <div>
        <form action="${pageContext.request.contextPath}/type/save" method="post" onsubmit="return checkTypeForm()">
            <input id="typeId" type="text" name="id" hidden>
            <p style="margin-left: 50px;">
                <span style="height: 40px;font-size:25px;font-family: 楷体;color: yellow">类型名:</span>
                <input id="typeName" type="text" name="typeName" style="height: 35px;width: 400px;border-radius: 8px">
                <span style="margin: 0 15px;"></span>
                <input type="submit" value="保存" style="height: 30px;border-radius:
                5px;border-width: 0">
                <span style="margin: 0 5px;"></span>
                <input type="button" onclick="clearForm()" value="清空" style="height: 30px;border-radius:
                5px;border-width: 0">
            </p>
        </form>
    </div>

    <div>
        <h3 style="margin-left: 50px;font-size:25px;font-family: 楷体;color: yellow">所有分类:</h3>
        <table style="margin: 10px 50px">
            <c:forEach items="${typeList}" var="type" varStatus="index">
                <tr >
                    <td align="right">${index.index+1}.</td><td width="5px"></td>
                    <td height="30px">
                        <span>${type.typeName}&nbsp;&nbsp;(${type.articleNum})</span>
                    </td>
                    <td width="40px"></td>
                    <td>
                        <a href="#" onclick="updateType('${type.id}','${type.typeName}')" id="update_btn">修改</a>
                        <span style="margin: 0 5px;"></span>
                        <a href="#" id="delete_btn" onclick="checkTypeDelete('${type.id}')">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<script>
    function checkTypeForm() {
        var typeName = $("#typeName").val();
        if(typeName == ''){
            layer.open({
                icon:7,offset: '200px',
                title: '错误提示',
                content: '请输入类型名！'
            });
            return false;
        }else {
            return true;
        }
    }

    function updateType(id,typeName) {
        $("#typeId").val(id);
        $("#typeName").val(typeName);
        $("#typeName").focus();
    }

    function clearForm() {
        $("#typeId").val("");
        $("#typeName").val("");
    }

    function checkTypeDelete(id) {
        layer.confirm('确定要删除吗？', {
            btn: ['确定','取消'], //按钮
            icon:3,
            closeBtn: 0,
            offset: '200px',
            title:false
        }, function(){
            $(location).prop('href', '${pageContext.request.contextPath}/type/delete/'+id);
        }, function(){
            layer.msg('取消删除!',{
                offset: '200px'
            });
        });
    }
</script>