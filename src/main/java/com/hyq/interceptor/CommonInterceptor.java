package com.hyq.interceptor;

import com.hyq.entity.Type;
import com.hyq.entity.User;
import com.hyq.service.BaseService;
import com.hyq.util.CheckUtil;
import com.hyq.util.Constants;
import com.hyq.util.PropUtil;
import com.hyq.util.StringUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class CommonInterceptor implements HandlerInterceptor{
    
    @Resource
    private BaseService baseService;
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        String servletPath = request.getServletPath();
        //获取所有需要登陆后才能访问的路径
        String[] privatePath = PropUtil.getValue(Constants.PATH_REGEX_PRIVATE).split(";");
        //如果用户已经登陆则直接放行
        if (CheckUtil.isNotNull(currentUser)){
            return true;
        }else if(StringUtil.matchPatterns(servletPath,privatePath)){
            //未登录访问隐私路径则全部跳转到登陆页面
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }else{
            //访问公共路径则放行
            return true;
        }
    }
    
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        //获取当前请求路径
        String servletPath = request.getServletPath();
        //获取请求哪些路径时需要刷新放在session中的type
        String[] refreshTypePath = PropUtil.getValue(Constants.REFRESH_TYPE_SESSION).split(";");
        //获取请求那些路径时需要刷新放在session中把文章按月分组的信息
        String[] refreshClassifyMonthPath = PropUtil.getValue(Constants.REFRESH_CLASSIFY_MONTH_SESSION).split(";");
    
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (CheckUtil.isNotNull(currentUser)){
            if(StringUtil.matchPatterns(servletPath,refreshTypePath)){
                //获取类型存入session中
                List<Type> typeList = baseService.find(Type.class,null,null);
                for (Type type : typeList){
                    type.setArticleNum(type.getArticleList().size());
                }
                session.setAttribute("typeList",typeList);
            }
            if(StringUtil.matchPatterns(servletPath,refreshClassifyMonthPath)){
                //获取按月分类存入session中
                Map<String,String> classifyByMonth = baseService.classifyArticleByMonth();
                session.setAttribute("classifyByMonth",classifyByMonth);
            }
        }
    }
    
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    
    }
}
