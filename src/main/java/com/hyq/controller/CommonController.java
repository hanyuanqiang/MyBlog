package com.hyq.controller;

import com.hyq.service.BaseService;
import com.hyq.util.Constants;
import com.hyq.util.ImageCodeUtil;
import com.hyq.util.PropUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 该类中的访问路径不用进行权限认证
 */
@Controller
public class CommonController {
    
    @Resource
    private BaseService baseService;
    
    @RequestMapping( "/imageCode")
    public void getImageCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream out=response.getOutputStream();
        Map<String,Object> result = ImageCodeUtil.genImgCode();
        ImageIO.write((RenderedImage) result.get("buffImg"), "png", out);
        session.setAttribute("imgCode",result.get("imgCode"));
        System.out.println(result.get("imgCode"));
        out.flush();
        out.close();
        response.flushBuffer();
    }
    
    @RequestMapping("/login")
    public String toLoginPage(){
        return "login";
    }
    
    @RequestMapping("/editor")
    public String editor() throws UnsupportedEncodingException {
        return PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"editor";
    }
    
}
