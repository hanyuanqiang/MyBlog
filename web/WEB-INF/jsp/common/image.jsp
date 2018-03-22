<%@ page import="com.hyq.util.ImageCodeUtil" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.RenderedImage" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/6
  Time: 0:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    OutputStream os=response.getOutputStream();
    Map<String,Object> result = ImageCodeUtil.genImgCode();
    ImageIO.write((RenderedImage) result.get("buffImg"), "png", os);
    session.setAttribute("imgCode",result.get("imgCode"));
    os.flush();
    os.close();
    response.flushBuffer();
    out.clear();
    out = pageContext.pushBody();
%>
