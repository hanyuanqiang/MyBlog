<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/27
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%--<html>
<head>
  <meta charset="UTF-8">
  <title>wangEditor demo list</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/highlight/styles/docco.css">
  <style type="text/css">
    .body {
      width: 800px;
      margin: 0 auto 0 auto;
    }
      code{
          border: solid #131dee 1px;
          font-size: 18px;
          font-family: Arial;
          background-color: #F6F6AE;
          line-height: 25px;
          padding: 15px 15px;
          border-radius: 15px;
      }
      pre{
          padding: 0px!important;
      }
  </style>
</head>
<body>
<div class="body">
  <p>可访问 <a href="http://www.wangeditor.com/" target="_blank">wangEditor 官网</a> 了解更多内容</p>
  <div id="div1">
    <p>欢迎使用 <b>wangEditor</b> 富文本编辑器</p>
  </div>
</div>


<div align="center">
    <p><button onclick="editor.$textElem.attr('contenteditable', true)">启用编辑器</button>
        <button onclick="editor.$textElem.attr('contenteditable', false)">禁用编辑器</button>
        <button onclick="editor.txt.clear()">清空内容</button>
        <button onclick="editor.txt.append('<p>追加的内容</p>')">追加内容</button>
        <button onclick="editor.txt.html('<p>用 JS 设置的内容</p>')">初始化内容</button>
    </p>
</div>

<div align="center">
    <p><button onclick="alert(editor.txt.html())">获取html</button>
        <button onclick="alert(editor.txt.text())">获取text</button>
        <button onclick=" hljs.initHighlightingOnLoad()">测试</button>
    </p>
</div>

<div>
    <p>
    <pre style="border: 1px red solid;">
        <code>public void deleteEntity(Class<T> c, int id) {
        try {
            T  o = (T) c.newInstance();
            Method met = o.getClass().getMethod("setId",Integer.class);
            met.invoke(o,id);
            myDAO.delete(o);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }</code>
    </pre>

    <pre style="border: 1px red solid;">
        <code>public void deleteEntity(Class<T> c, int id) {
        try {
            T  o = (T) c.newInstance();
            Method met = o.getClass().getMethod("setId",Integer.class);
            met.invoke(o,id);
            myDAO.delete(o);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }</code>
    </pre>
    </p>
</div>

<!-- 引用js -->
<script src="${pageContext.request.contextPath}/static/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/wangEditor/release/wangEditor.js"></script>
<script src="${pageContext.request.contextPath}/static/highlight/highlight.pack.js"></script>
<script type="text/javascript">
    hljs.initHighlightingOnLoad();
    var E = window.wangEditor;
    var editor = new E('#div1');

    // 关闭粘贴样式的过滤
    editor.customConfig.pasteFilterStyle = false;
    // 自定义处理粘贴的文本内容
    editor.customConfig.pasteTextHandle = function (content) {
        // content 即粘贴过来的内容（html 或 纯文本），可进行自定义处理然后返回
        alert(content);
        return content;
    }

    editor.customConfig.zIndex = 100;

    editor.create();

</script>

</body>
</html>--%>
<html>
<head>
    <meta charset="UTF-8">
    <title>wangEditor 查看源码</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/highlight/styles/docco.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/template/template.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/my.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/layer/mobile/need/layer.css">

    <script src="${pageContext.request.contextPath}/static/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/highlight/highlight.pack.js"></script>
    <script src="${pageContext.request.contextPath}/static/layer/layer.js"></script>

    <style type="text/css">
        #container {
            width: 800px;
            margin: 0 auto;
        }
        #toolbar-container {
            border: 1px solid #ccc;
            background-color: #fff;
        }
        #toolbar-container:after {
            display: table;
            content: '';
            clear: both;
        }
        #editor-toolbar {
            float: left;
        }
        #btn-container {
            float: right;
            text-align: right;
        }
        #editor-text {
            border: 1px solid #ccc;
            border-top: 0;
            height: 300px;
            background-color: #fff;
        }
    </style>
</head>
<body>
<p>wangEditor 全屏</p>

<!--非全屏模式-->
<div id="container">
    <!--菜单栏-->
    <div id="toolbar-container">
        <table>
            <tr>
                <td>
                    <div id="editor-toolbar"></div>
                </td>
                <td>
                    <div id="btn-container">
                        <img src="${pageContext.request.contextPath}/static/wangEditor/myIcon/html_code.png">
                        <%--<i id="btn1" class="fal fa-file-code"></i>--%>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <!--编辑区域-->
    <div id="editor-text">
        <p>wangEditor 本身不包含“全屏”功能，不过可以很简单的开发出来</p>
        <p>注意，全屏模式与<code>max-height</code>有冲突，尽量避免一起使用</p>
    </div>
</div>
<div style="margin: 50px auto;border: 1px solid red">
    <p align="center"><span style="color: #c39b63;font-size: 16px;margin-right: 10px">该文章已加密</span><button style="cursor: pointer;border-radius: 5px" onclick="decryptMethod()">我要查看</button></p>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/wangEditor/release/wangEditor.min.js"></script>

<script type="text/javascript">
    // 创建编辑器
    var E = window.wangEditor
    var editor2 = new E('#editor-toolbar', '#editor-text')
    editor2.create()

    /*// 获取使用到的元素
    var toolbarContaner = document.getElementById('toolbar-container')
    var editorText = document.getElementById('editor-text')
    var cover = document.getElementById('cover')
    var container = document.getElementById('container')

    // 全屏事件
    function doFullScreen() {
        cover.style.display = 'block'
        editorText.style.height = '460px';
        cover.appendChild(toolbarContaner)
        cover.appendChild(editorText)
    }

    // 退出全屏事件
    function unDoFullScreen() {
        container.appendChild(toolbarContaner)
        container.appendChild(editorText)
        editorText.style.height = '300px';
        cover.style.display = 'none'
    }

    // 是否是全屏的标志
    var isFullScreen = false

    // 点击事件
    var btn1 = document.getElementById('btn1')
    btn1.addEventListener('click', function () {
        if (isFullScreen) {
            isFullScreen = false
            unDoFullScreen()
        } else {
            isFullScreen = true
            doFullScreen()
        }
    }, false)*/


</script>

<script>

    var users = new Array();
    var index = 0;
    var intervalTime = 1000 * 2;
    var executeTime = 1000 * 20;
    setInterval(function () {
        var text = $(".jschartli .name .js-nick");
        text.each(function(){
            var $this = $(this);
            var nick = $this.text();
            if(users.indexOf(nick)<0){
                users[index] = nick;
                index++;
            }
        });
    },intervalTime);
    setTimeout(function () {
        alert("在"+(executeTime/1000)+"s内进入直播间的人数是"+users.length);
    },executeTime);


    var c1 = "111";
    var c2 = "222";
    var time = 6000;
    var tag = true;
    setInterval(function () {
        if(tag){
            $("#js-send-msg .cs-textarea").val(c1);
        }else{
            $("#js-send-msg .cs-textarea").val(c2);
        }
        $("#js-send-msg .b-btn").click();
        tag = !tag;
    },time);

</script>

</body>
</html>