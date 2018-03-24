package com.hyq.test;

import org.apache.log4j.Logger;

public class LoggerTest {
    
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(LoggerTest.class);
        logger.error("这是错误消息");
        logger.info("这是正常消息");
        logger.debug("这是调试信息");
        try{
            int a = 1/0;
        }catch (Exception e){
            logger.error(e);
        }
    }
    
}
