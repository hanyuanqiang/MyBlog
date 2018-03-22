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

<style type="text/css">
    .edit_toolbar {
        border: 1px solid #ccc;
    }
    .edit_content {
        border: 1px solid #ccc;
    }
</style>

<div id="article_editor">
    <form action="${pageContext.request.contextPath}/article/save" onsubmit="return checkArticleForm()" method="post">
        <input type="text" name="id" value="${article.id}" hidden>
        <textarea id="hidden_content" name="content" hidden></textarea>

        <table style="width: 100%;">
            <tr style="height: 40px;">
                <td align="right" width="10%"><span class="article_edit_label">标题：</span></td>
                <td colspan="5">
                    <input class="article_edit_input" type="text" name="title" value="${article.title}">
                </td>
            </tr>
            <tr style="height: 15px;"></tr>
            <tr>
                <td align="right" style="vertical-align: top"><span class="article_edit_label">内容：</span></td>
                <td colspan="5" style="vertical-align: top;">
                    <div id="edit_toolbar" class="edit_toolbar" style="width:790px;">
                        <img src="${pageContext.request.contextPath}/static/icons/html_code.png"
                             style="margin-top: 4px;cursor:pointer;height: 18px;width: 20px;" onclick="showHtmlCode()"
                            title="查看html">
                    </div>
                    <div id="edit_content" class="edit_content" style="height: 700px;width:790px;">
                        ${article.content}
                    </div>
                </td>
            </tr>
            <tr style="height: 45px;"></tr>
            <tr>
                <td align="right" width="10%"><span class="article_edit_label">分类：</span></td>
                <td width="23%">
                    <select name="type.id" style="height: 30px;width: 100%">
                        <c:forEach items="${typeList}" var="type">
                            <option value="${type.id}" ${article.type.id == type.id ? 'selected':''}>${type.typeName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right" width="10%"><span class="article_edit_label">权限：</span></td>
                <td width="23%">
                    <input type="radio" name="visitAuth" value="所有人可见" checked>所有人
                    <input type="radio" name="visitAuth" value="仅自己可见" ${article.visitAuth.name == 'onlyMyself'?'checked':''}>仅自己
                </td>
                <td align="right" width="10%"><span class="article_edit_label">来源：</span></td>
                <td width="23%">
                    <input type="radio" name="source" value="原创" checked>原创
                    <input type="radio" name="source" value="转载" ${article.source.name == 'reprint'?'checked':''}>转载
                </td>
            </tr>
            <tr style="height: 15px;"></tr>
            <tr>
                <td align="right"><span class="article_edit_label">关键词：</span></td>
                <td colspan="5"><input class="article_edit_input" type="text" name="keywords" value="${article.keywords}"></td>
            </tr>
            <tr style="height: 15px;"></tr>
            <tr>
                <td colspan="6" align="right">
                    <input id="publish_button" type="submit" value="发布">
                </td>
            </tr>
        </table>
    </form>
    <textarea id="content_html_code" style="width:680px; min-height: 420px;margin: 10px" hidden></textarea>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/wangEditor/release/wangEditor.js"></script>
<script type="text/javascript">
    var E = window.wangEditor;
    var editor = new E('#edit_toolbar','#edit_content');
    editor.customConfig.uploadImgServer = '${pageContext.request.contextPath}/article/upload'
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
    editor.customConfig.uploadImgTimeout = 1000000;
    editor.customConfig.uploadImgMaxLength = 5;
    editor.customConfig.uploadFileName = 'articleFile';
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $("#content_html_code").val(html);
    };
    editor.create();
    $("#content_html_code").val(editor.txt.html());

    function checkArticleForm() {
        var title = $("#input_title").val();
        var content = editor.txt.text();

        if(title =='' || content ==''){
            layer.open({
                icon:7,offset: '200px',
                title: '错误提示',
                content: '请输入标题和内容！'
            });
            return false;
        }else{
            $("#hidden_content").val(editor.txt.html());
            return true;
        }
    }

    function showHtmlCode() {
        // alert(editor.txt.html());
        layer.open({
            type:1,
            offset: '200px',
            area: ['700px', '500px'], //宽高
            title: '查看源码',
            scrollbar: false,
            content: $('#content_html_code')
        });
    }
</script>