package com.hyq.test;

import com.hyq.util.ImageCodeUtil;
import com.hyq.util.PropUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;


public class BeanTest {

    public static void main(String[] args) throws IOException{
        for (int i = 0;i<40;i++){
            ImageCodeUtil.write("G:/image/"+i+".png", (BufferedImage) ImageCodeUtil.genImgCode().get("buffImg"));
        }
        System.out.println("success");
    }

}