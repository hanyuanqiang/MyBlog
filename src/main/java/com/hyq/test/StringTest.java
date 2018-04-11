package com.hyq.test;

import com.hyq.util.GetterUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class StringTest {
    
    public static void main(String[] args) throws IOException {
        String[] nadoUserArray = FileUtils.readFileToString(new File("F://斗鱼黑屏统计/纳豆.txt")).split(",");
        String[] xyUserArray = FileUtils.readFileToString(new File("F://斗鱼黑屏统计/小缘.txt")).split(",");
        String[] erkeUserArray = FileUtils.readFileToString(new File("F://斗鱼黑屏统计/二珂.txt")).split(",");
        String[] alUserArray = FileUtils.readFileToString(new File("F://斗鱼黑屏统计/啊冷.txt")).split(",");
        String[] yifaUserArray = FileUtils.readFileToString(new File("F://斗鱼黑屏统计/陈一发.txt")).split(",");
        
        //nadoUserArray     xyUserArray     erkeUserArray   alUserArray     yifaUserArray
        System.out.println("纳豆用户数："+nadoUserArray.length);
        System.out.println("小缘用户数："+xyUserArray.length);
        System.out.println("二珂用户数："+erkeUserArray.length);
        System.out.println("啊冷用户数："+alUserArray.length);
        System.out.println("一发用户数："+yifaUserArray.length);
        
        int sum = 0;
        for (String user : nadoUserArray){
            int s = user.indexOf("-");
            String gradeStr = user.substring(s+1);
            int grade = GetterUtil.getInteger(gradeStr);
            sum += grade;
        }
        double avg = sum/GetterUtil.getDouble(nadoUserArray.length);
        System.out.println("纳豆平均等级："+avg);
    
        sum = 0;
        for (String user : xyUserArray){
            int s = user.indexOf("-");
            String gradeStr = user.substring(s+1);
            int grade = GetterUtil.getInteger(gradeStr);
            sum += grade;
        }
        avg = sum/GetterUtil.getDouble(xyUserArray.length);
        System.out.println("小缘平均等级："+avg);
    
        sum = 0;
        for (String user : erkeUserArray){
            int s = user.indexOf("-");
            String gradeStr = user.substring(s+1);
            int grade = GetterUtil.getInteger(gradeStr);
            sum += grade;
        }
        avg = sum/GetterUtil.getDouble(erkeUserArray.length);
        System.out.println("二珂平均等级："+avg);
    
        sum = 0;
        for (String user : alUserArray){
            int s = user.indexOf("-");
            String gradeStr = user.substring(s+1);
            int grade = GetterUtil.getInteger(gradeStr);
            sum += grade;
        }
        avg = sum/GetterUtil.getDouble(alUserArray.length);
        System.out.println("啊冷平均等级："+avg);
    
        sum = 0;
        for (String user : yifaUserArray){
            int s = user.indexOf("-");
            String gradeStr = user.substring(s+1);
            int grade = GetterUtil.getInteger(gradeStr);
            sum += grade;
        }
        avg = sum/GetterUtil.getDouble(yifaUserArray.length);
        System.out.println("一发平均等级："+avg);
    }
    
    
    
}
