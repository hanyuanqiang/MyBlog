package com.hyq.util;

import java.util.UUID;

public class GetterUtil {

    public static final boolean DEFAULT_BOOLEAN = false;
    public static final double DEFAULT_DOUBLE = 0.0;
    public static final float DEFAULT_FLOAT = 0;
    public static final int DEFAULT_INTEGER = 0;
    public static final long DEFAULT_LONG = 0;
    public static final short DEFAULT_SHORT = 0;
    public static final String DEFAULT_STRING = "";

    public static final String[] BOOLEAN_TRUE = {"true", "t", "y", "on", "1"};
    public static final String[] BOOLEAN_FALSE = {"false", "f", "n", "off", "0"};

    public static boolean getBoolean(Object value){
        return getBoolean(value,DEFAULT_BOOLEAN);
    }
    public static boolean getBoolean(Object value,boolean defaultValue){
        if (value != null) {
            String svalue = value.toString().trim();
            for (String s : BOOLEAN_TRUE){
                if (s.equalsIgnoreCase(svalue)){
                    return true;
                }
            }
            for (String s : BOOLEAN_FALSE){
                if (s.equalsIgnoreCase(svalue)){
                    return false;
                }
            }
        }
        return defaultValue;
    }
    
    public static double getDouble(Object value){
        return getDouble(value,DEFAULT_DOUBLE);
    }
    public static double getDouble(Object value,double defaultValue){
        try {
            return Double.parseDouble(value.toString().trim());
        }catch (Exception e){
        }
        return defaultValue;
    }

    public static int getInteger(Object value){
        return getInteger(value,DEFAULT_INTEGER);
    }
    public static int getInteger(Object value,int defaultValue){
        try {
            return Integer.parseInt(value.toString().trim());
        }catch (Exception e){
        }
        return defaultValue;
    }

    public static float getFloat(Object value){
        return getFloat(value,DEFAULT_FLOAT);
    }
    public static float getFloat(Object value,float defaultValue){
        try {
            return Float.parseFloat(value.toString().trim());
        }catch (Exception e){
        }
        return defaultValue;
    }

    public static long getLong(Object value){
        return getLong(value,DEFAULT_LONG);
    }
    public static long getLong(Object value,long defaultValue){
        try {
            return Long.parseLong(value.toString().trim());
        }catch (Exception e){
        }
        return defaultValue;
    }

    public static short getShort(Object value){
        return getShort(value,DEFAULT_SHORT);
    }
    public static short getShort(Object value,short defaultValue){
        try {
            return Short.parseShort(value.toString().trim());
        }catch (Exception e){
        }
        return defaultValue;
    }

    public static String getString(Object value){
        return getString(value,DEFAULT_STRING);
    }

    public static String getString(Object value,String defaultValue){
        if (CheckUtil.isNotNull(value)){
            return value.toString().trim();
        }
        return defaultValue;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

}
