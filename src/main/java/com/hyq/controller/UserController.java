package com.hyq.controller;


import com.hyq.entity.User;
import com.hyq.service.BaseService;
import com.hyq.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private BaseService baseService;
    
    @RequestMapping( "/login")
    public String login(@Validated User loginUser, HttpServletRequest request, Map<String,Object> map,@RequestParam String imageCode) throws Exception {
        if (CheckUtil.isNull(loginUser.getAccount()) ||
                CheckUtil.isNull(loginUser.getPassword())){
            map.put("errorInfo","请输入用户名或密码！");
        }else if(!GetterUtil.getString(request.getSession().getAttribute("imgCode")).equalsIgnoreCase(imageCode)){
            map.put("errorInfo","验证码不正确！");
        }else{
            Map<String,Object> conditions = new HashMap<String, Object>();
            conditions.put("account",loginUser.getAccount());
            conditions.put("password",StringUtil.getMd5(loginUser.getPassword()));
            User currentUser = (User) baseService.find(User.class,conditions);
            if (CheckUtil.isNotNull(currentUser)){
                HttpSession session = request.getSession();
                session.setAttribute("currentUser",currentUser);
                return "redirect:/article/list";
            }else{
                map.put("errorInfo","用户名或密码错误！");
            }
        }
        return "login";
    }
    
    @RequestMapping( "/center")
    public String center(Map<String,Object> map) throws Exception {
        map.put("subPage", PropUtil.getValue(Constants.USER_JSP_LOCATION)+"userManager.jsp");
        return PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"main";
    }
    
    @RequestMapping( value = "/save",method = RequestMethod.POST)
    public String save(Map<String,Object> map,User newUser,HttpServletRequest request,@RequestParam(value = "file",required = false)CommonsMultipartFile file)throws Exception{
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        //下面三个属性不能更改
        newUser.setId(currentUser.getId());
        newUser.setPassword(currentUser.getPassword());
        newUser.setAccount(currentUser.getAccount());
        
        String filePath = request.getServletContext().getRealPath("/files/avatar/");
        if (CheckUtil.isNotNull(file) && CheckUtil.isNotEmpty(file.getOriginalFilename())){
            if (file.getContentType().startsWith("image/")){
                String oldFileName = file.getOriginalFilename();
                String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
                String newFileName = GetterUtil.getUUID()+suffix;
                String finalFilePath = filePath+newFileName;
                System.out.println("上传位置及新文件名："+finalFilePath);
                file.transferTo(new File(finalFilePath));
                String newAvatarUrl = request.getContextPath()+"/files/avatar/"+newFileName;
                newUser.setAvatar(newAvatarUrl);
                System.out.println("头像新url："+newAvatarUrl);
            }else{
                map.put("errorMsg","请上传图片最为头像！");
            }
        }else {
            newUser.setAvatar(currentUser.getAvatar());
        }
        baseService.update(newUser);
        
        request.getSession().setAttribute("currentUser",newUser);
        map.put("subPage", PropUtil.getValue(Constants.USER_JSP_LOCATION)+"userManager.jsp");
        return PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"main";
    }
    
    @RequestMapping( "/logout")
    public String logout(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return "login";
    }
}
