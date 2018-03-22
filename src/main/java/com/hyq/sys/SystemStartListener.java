package com.hyq.sys;


import com.hyq.dao.BaseDao;
import com.hyq.entity.User;
import com.hyq.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 该类可以在系统启动时可以监听各个阶段
 */
@WebListener
@WebFilter
public class SystemStartListener extends HttpServlet implements WebApplicationInitializer,ServletContextListener,Filter {
    
    //Spring配置框架加载之前执行
    public void onStartup(ServletContext servletContext) throws ServletException {
        try {
            String dbUserName = PropUtil.getValue(Constants.JDBC_USERNAME);
            String dbPassword = PropUtil.getValue(Constants.JDBC_PASSWORD);
            String dbName = PropUtil.getValue(Constants.JDBC_DBNAME);
            String sqlFilePath = PropUtil.getValue(Constants.SQL_FILE_PATH);
        
            String statement = "cmd.exe /C  mysqldump  -u"+dbUserName+" -p"+dbPassword+" "+dbName+">"+sqlFilePath;
            Runtime.getRuntime().exec(statement);
        
            Thread.sleep(1000); //这里线程睡眠3秒的作用是防止上面语句执行了还未生成文件，下面的语句就会报错
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Spring配置框架加载之前执行,执行顺序：1");
        
        //下面代码可以代替web.xml中SpringMvc的配置
        XmlWebApplicationContext xwac = new XmlWebApplicationContext();
        xwac.setConfigLocation("classpath*:/spring-mvc.xml");
        ServletRegistration.Dynamic defServlet01 = servletContext.addServlet("springMvc",
                new DispatcherServlet(xwac));
        defServlet01.setLoadOnStartup(1);
        defServlet01.addMapping("/");
    
        ServletRegistration.Dynamic defServlet02 = servletContext.addServlet("SystemStartListener", new SystemStartListener());
        defServlet02.setLoadOnStartup(2);
    }
    
    //应用启动完成后执行
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("执行顺序：2");
    }
    
    //应用关闭后执行
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("应用关闭");
    }
    
    //这是Filter的初始化方法，注意参数，跟下面的init方法不一样
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("执行顺序：3");
    }
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    
    }
    
    public void destroy() {
    
    }
    
    //由load-on-startup决定执行顺序，该方法继承自HttpServlet
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext app  = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        BaseDao bd = (BaseDao) app.getBean("baseDao");
        User admin = (User) bd.get(User.class,1);
        if (CheckUtil.isNull(admin)){
            admin = new User();
            try {
                admin.setAccount(PropUtil.getValue(Constants.ADMIN_ACCOUNT));
                admin.setPassword(StringUtil.getMd5(PropUtil.getValue(Constants.ADMIN_PASSWORD)));
                bd.save(admin);
            } catch (UnsupportedEncodingException e) {
                System.out.println("添加默认管理员失败");
                e.printStackTrace();
            }
        }
        try {
            String sqlFilePath = PropUtil.getValue(Constants.SQL_FILE_PATH);
            String sqlFileEncryptPath = PropUtil.getValue(Constants.SQL_FILE_ENCRYPT_PATH);
            
            String sqlString = FileUtils.readFileToString(new File(sqlFilePath),"utf-8");
            String sqlEncryptString = StringUtil.XOREncrypt(sqlString);
            FileUtils.writeStringToFile(new File(sqlFileEncryptPath),sqlEncryptString,"utf-8");
            if (GetterUtil.getBoolean(PropUtil.getValue(Constants.IS_DELETE_SQL_FILE))){
                FileUtils.deleteQuietly(new File(sqlFilePath));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("执行顺序：4");
    }
    
}
