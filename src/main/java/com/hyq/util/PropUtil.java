package com.hyq.util;


import org.apache.log4j.Logger;

import java.io.*;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 提供一些常用的属性文件相关的方法
 */
public final class PropUtil {
    public static Logger logger = Logger.getLogger(PropUtil.class);
    private final static String[] propFiles = new String[]{"sys.properties","my.properties"};
    /**
     * 从系统属性文件中获取相应的值
     *
     * @param key key
     * @return 返回value
     */
    public final static String key(String key) {
        return System.getProperty(key);
    }

    /**
     * 根据Key读取Value
     *
     * @param filePath 属性文件
     * @param key      需要读取的属性
     */
    public final static String getValueByKey(String filePath, String key) throws UnsupportedEncodingException {
        filePath = getDefaultPath()+filePath;
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            pps.load(in);
            return pps.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 逐一扫描配置文件
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public final static String getValue(String key) throws UnsupportedEncodingException {
        Properties pps = new Properties();
        for (String propFile : propFiles){
            String filePath = getDefaultPath()+propFile;
            try {
                InputStream in = new BufferedInputStream(new FileInputStream(filePath));
                pps.load(in);
                String result = pps.getProperty(key);
                if (CheckUtil.isNotEmpty(result)){
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    

    public final static Map<String,String> getProperties(InputStream in){
        Map<String,String> map = new HashMap<String,String>();
        Properties pps = new Properties();
        try {
            pps.load(in);
        } catch (IOException e) {
            logger.error("load properties error:"+e.getMessage());
        }
        Enumeration en = pps.propertyNames();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = pps.getProperty(strKey);
            map.put(strKey,strValue);
        }
        return map;
    }
    /**
     * 读取Properties的全部信息
     *
     * @param filePath 读取的属性文件
     * @return 返回所有的属性 key:value<>key:value
     */
    public final static Map<String,String> getAllProperties(String filePath) throws IOException {
        Map<String,String> map = new HashMap<String,String>();
        Properties pps = new Properties();
        try  {
            InputStream in = new BufferedInputStream(new FileInputStream(getDefaultPath()+filePath));
            return getProperties(in);
        }catch (IOException e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 写入Properties信息
     *
     * @param filePath 写入的属性文件
     * @param pKey     属性名称
     * @param pValue   属性值
     */
    public final static void writeProperties(String filePath, String pKey, String pValue) throws IOException {
        Properties props = new Properties();
        filePath = getDefaultPath()+filePath;
        props.load(new FileInputStream(filePath));
        // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream fos = new FileOutputStream(filePath);
        props.setProperty(pKey, pValue);
        // 以适合使用 load 方法加载到 Properties 表中的格式，
        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
        props.store(fos, "Update '" + pKey + "' value");

    }

    /**
     * 删除指定键值对
     * @param filePath
     * @param key
     * @throws IOException
     */
    public final static void removeProperties(String filePath,String key) throws IOException{
        Properties props = new Properties();
        filePath = getDefaultPath()+filePath;
        props.load(new FileInputStream(filePath));
        props.remove(key);
        OutputStream fos = new FileOutputStream(filePath);
        props.store(fos,"Delete "+key);
    }


    private final static String getDefaultPath() throws UnsupportedEncodingException {
        return URLDecoder.decode(PropUtil.class.getResource("/").getPath(),"UTF-8");
    }
}
