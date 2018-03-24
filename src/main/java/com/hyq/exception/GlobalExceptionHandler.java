package com.hyq.exception;

import com.hyq.util.Constants;
import com.hyq.util.PropUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends DefaultHandlerExceptionResolver {
    
    private static final Logger _log = Logger.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response,Object o,Exception ex){
        _log.error("请求路径为："+request.getServletPath());
        _log.error("输出异常信息："+ex.getMessage());
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMsg","服务器发生错误");
        try {
            mav.setViewName(PropUtil.getValue(Constants.COMMON_JSP_LOCATION)+"error");
        } catch (Exception e) {
            _log.error(ex);
        }
        return mav;
    }
}
