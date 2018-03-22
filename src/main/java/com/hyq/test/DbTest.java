package com.hyq.test;

import com.hyq.util.PropUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbTest {
    public static void main(String[] args) throws Exception {
        /*//驱动程序名
        String driver = PropUtil.getValueByKey("sys.properties","jdbc.driverClassName");
        //URL指向要访问的数据库名mydata
        String url = PropUtil.getValueByKey("sys.properties","jdbc.url");
        System.out.println(driver);
        System.out.println(url);

        String user = "root";
        String password = "8235143";
        Connection con;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myblog?useSSL=true",user,password);
            System.out.println("success!");
        }catch (Exception e){
            e.printStackTrace();
        }*/
    
//        Runtime.getRuntime().exec("cmd.exe /C  mysqldump  -uroot -p8235143  myblog>f:/test.sql");
    }
}
