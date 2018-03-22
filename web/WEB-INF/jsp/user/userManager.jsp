<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/9
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div>

    <form action="${pageContext.request.contextPath}/user/save" method="post" enctype="multipart/form-data">
        <table style="margin: 50px;width: 800px;">
            <tr>
                <td align="right" width="50px">
                    <span class="user_edit_label">头像:</span>
                </td>
                <td style="width: 10px"></td>
                <td style="vertical-align: center">
                    <img src="${currentUser.avatar}" height="50px">
                    <a class="update_file">修改头像
                        <input style="cursor:pointer;" type="file" name="file" id="file_upload_btn" onchange="fileChange()">
                    </a>
                    <span id="upload_img_name"></span>
                </td>
            </tr>
            <tr style="height: 10px">
                <td colspan="3">
                    <p style="height: 0;border-bottom: 1px #565600 solid"></p>
                </td>
            </tr>
            <tr>
                <td align="right"><span class="user_edit_label">名称:</span></td>
                <td style="width: 10px"></td>
                <td><input style="width: 180px" class="article_edit_input" type="text" name="nickName" value="${currentUser.nickName}"></td>
            </tr>
            <tr style="height: 10px">
                <td colspan="3">
                    <p style="height: 0;border-bottom: 1px #565600 solid"></p>
                </td>
            </tr>
            <tr>
                <td align="right"><span class="user_edit_label">签名:</span></td>
                <td style="width: 10px"></td>
                <td><input class="article_edit_input" type="text" name="signature" value="${currentUser.signature}"></td>
            </tr>
            <tr style="height: 10px">
                <td colspan="3">
                    <p style="height: 0;border-bottom: 1px #565600 solid"></p>
                </td>
            </tr>
            <tr>
                <td align="right" style="vertical-align: top"><span class="user_edit_label">简介:</span></td>
                <td style="width: 10px"></td>
                <td>
                    <textarea style="height: 200px;width: 600px" class="article_edit_input" name="introduce">${currentUser.introduce}</textarea>
                </td>
            </tr>
            <tr style="height: 10px">
                <td colspan="3">
                    <p style="height: 0;border-bottom: 1px #565600 solid"></p>
                </td>
            </tr>
            <tr>
                <td align="right" colspan="3">
                    <input type="submit" value="保存" style="height: 30px;border-radius:
                5px;border-width: 0;margin-right: 100px">
                </td>
            </tr>
        </table>
    </form>

</div>

<script>

    function fileChange() {
        var str = $("#file_upload_btn").val();
        if(str!=''){
            $("#upload_img_name").text(str);
        }
    }

</script>