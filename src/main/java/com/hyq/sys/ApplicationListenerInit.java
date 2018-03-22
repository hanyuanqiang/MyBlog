package com.hyq.sys;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationListenerInit implements ApplicationListener<ContextRefreshedEvent> {
    
    //下面注解可以执行成功，即baseDao不为null
    /*@Resource
    private BaseDao<User> baseDao;*/
    
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //项目中有Spring mvc时该监听器会执行两次
//        BaseDao bd = (BaseDao) contextRefreshedEvent.getApplicationContext().getBean("baseDao");
        System.out.println("每次Spring配置文件加载完成后执行.....");
    }
}
