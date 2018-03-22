package com.hyq.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoSomething {

    @Autowired
    public void doIt(People people){


        System.out.println(people.getName()+"做了一些事");
        System.out.println("重新设置people的年龄为44");
        try {
            people.setAge(44);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
