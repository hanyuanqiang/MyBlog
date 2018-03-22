package com.hyq.test;

import com.hyq.util.PropUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PropTest {

    public static void main(String[] args) throws IOException {
        PropUtil.removeProperties("my.properties","myKeeey1");
        Map<String,String> props = PropUtil.getAllProperties("my.properties");
        Set<String> keys = props.keySet();
        for (String key : keys){
            System.out.println(key+"="+props.get(key));
        }
    }

}
