package com.hyq.test;

import org.springframework.stereotype.Component;

@Component
public class People {

    private int age = 12;
    private String name = "韩远强";
    private String desc = "是个好人";

    public People() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws Exception{
        this.age = age;
        int a = 1/0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
