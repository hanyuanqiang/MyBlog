<%@ page import="com.hyq.entity.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/28
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>个人博客后台登录</title>
  <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
  <style type="text/css">
    body{
      font:14px/28px "微软雅黑";
    }
    .contact *:focus{outline :none;}
    .contact{
      width: 700px;
      height: auto;
      background: #f6f6f6;
      margin: 0px auto;
      padding: 10px;
    //border: 1px red solid;
    }
    .contact ul {
      width: 650px;
      margin: 0 auto;
    }
    .contact ul li{
      border-bottom: 1px solid #dfdfdf;
      list-style: none;
      padding: 12px;
      /*margin: 0px 40px;*/
    }
    .contact ul li label {
      width: 120px;
      display: inline-block;
      float: left;
    }
    .contact ul li input[type=text],.contact ul li input[type=password]{
      width: 220px;
      height: 25px;
      border :1px solid #aaa;
      padding: 3px 8px;
      border-radius: 5px;
    }
    .contact ul li input:focus{
      border-color: #c00;

    }
    .contact ul li input[type=text]{
      transition: padding .25s;
      -o-transition: padding  .25s;
      -moz-transition: padding  .25s;
      -webkit-transition: padding  .25s;
    }
    .contact ul li input[type=password]{
      transition: padding  .25s;
      -o-transition: padding  .25s;
      -moz-transition: padding  .25s;
      -webkit-transition: padding  .25s;
    }
    .contact ul li input:focus{
      padding-right: 70px;
    }
    .btn{
      position: relative;
      left: 180px;
    }

    #myTitle{
      margin-top: 80px;
      margin-bottom:20px;
      font-size: 40px;
      font-family: 华文隶书;
      color: #044942;
    //border: 1px red solid;
      height: 30px;
    }
  </style>
  <script type="text/javascript">
      function checkForm(){
          var account = $("#account").val();
          var password = $("#password").val();
          var imageCode = $("#imageCode").val();
          if(account == ''){
              $("#account").focus();
              return false;
          }else if (password == ''){
              $("#password").focus();
              return false;
          }else if(imageCode == ''){
              $("#imageCode").focus();
              return false;
          }
          return true;
      }

      function findPass(){
      }

  </script>
</head>
<body>

<p id="myTitle" align="center">博客登陆</p>
<div class="contact" >
  <form name="form1" method="post" onsubmit="return checkForm()" action="${pageContext.request.contextPath}/user/login">
    <ul>
      <li>
        <label>姓&nbsp&nbsp&nbsp&nbsp名：</label>
        <input type="text" value="${user.account}" name="account" id="account" placeholder="用户名"/>
      </li>
      <li>
        <label>密&nbsp&nbsp&nbsp&nbsp码：</label>
        <input type="password" value="${user.password}" name="password" id="password" placeholder="密码"/>
      </li>
      <li>
        <label>验证码：</label>
        <input type="text" name="imageCode" id="imageCode" placeholder="验证码" style="width: 150px;" onkeydown="if(event.keyCode==13) $('form').submit()"/>
        <img onclick="javascript:randImage.src='${pageContext.request.contextPath}/imageCode?tm='+Math.random()" title="换一张试试" name="randImage"
             id="randImage" src="${pageContext.request.contextPath}/imageCode" width="60" height="24"
             align="absmiddle">
      </li>
    </ul>
    <p></p>
    <b class="btn">
      <input type="submit" value="登录"/>
      <input type="reset" value="取消" style="margin-left: 30px;"/>
      <input type="button" value="找回密码" style="margin-left: 30px;"  onclick="findPass()"/>
      <span style="color: red;padding-left: 20px;">${errorInfo}</span>
    </b>

  </form>
</div>

</body>
</html>
