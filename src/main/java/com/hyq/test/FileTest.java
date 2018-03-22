package com.hyq.test;

import com.hyq.util.StringUtil;

public class FileTest {
    
    public static void main(String[] args) throws Exception {
        String content = "韩远强。sdjf发电发电公司大幅改善了lajd啊打发打发sf";
        String newString = StringUtil.XOREncrypt(content);
        System.out.println(newString);
        System.out.println(StringUtil.XORDecrypt(newString));
    }
}
