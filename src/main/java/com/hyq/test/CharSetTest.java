package com.hyq.test;

import com.mchange.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CharSetTest {

    public static void main(String[] args) throws IOException {
        byte[] bytes = FileUtils.getBytes(new File("D:/重装后必看/sqlfile.sql"));
        String s = new String(bytes,"utf-8");
        System.out.println(s);
    }

}
