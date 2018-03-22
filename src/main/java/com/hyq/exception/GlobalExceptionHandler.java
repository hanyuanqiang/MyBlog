package com.hyq.exception;

import com.hyq.util.Constants;
import com.hyq.util.PropUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {
    
    @ExceptionHandler
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response,Object o,Exception ex){
        System.out.println("输出异常信息："+ex.getMessage());
        System.out.println(request.getServletPath());
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMsg","服务器发生错误");
        try {
            mav.setViewName(PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"error");
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mav;
    }

}
