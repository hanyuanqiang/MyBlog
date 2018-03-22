package com.hyq.util;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class CheckUtil {

    /**
     * 判断一个对象是否为null
     * @param o
     * @return
     */
    public static boolean isNull(Object o){
        return o == null;
    }

    /**
     * 判断一个对象是否不为null
     * @param o
     * @return
     */
    public static boolean isNotNull(Object o){
        return !isNull(o);
    }

    /**
     * 判断一个对象是否为空
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o){
        if (o == null){
            return true;
        }else if (o instanceof String){
            return String.valueOf(o).trim().length() == 0 ? true : false;
        }else if(Collection.class.isAssignableFrom(o.getClass())){
            return ((Collection)o).size() == 0 ? true : false;  //如果集合中，元素个数为0也返回true
        }else if(o instanceof Map){
            return ((Map) o).keySet().size() == 0 ? true : false;   //如果是Map,如果没有键值对则返回true
        }else if("String[]".equals(o.getClass().getSimpleName())) {
            String[] strings = (String[])o;
            for (String s : strings){
                if (s != null){
                    return false;
                }
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断一个对象是否不为空
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o){
        return !isEmpty(o);
    }

    /**
     * 判断两个对象是否相等
     * @param o1
     * @param o2
     * @return
     * @throws ParseException
     */
    public static boolean isEqual(Object o1,Object o2) throws ParseException {

        String o1ClazzName = o1.getClass().getName();
        String o2ClazzName = o2.getClass().getName();
        //首先判断两个对象是否同一种类型
        if ( ! o1ClazzName.equals(o2ClazzName)) {
            return false;
        } else if (o1 == o2){
            return true;
        }else if (o1.equals(o2)){
            return true;
        }else{
            if (o1 instanceof Date){
                String d1 = DateUtil.transDate2Str((Date) o1);
                String d2 = DateUtil.transDate2Str((Date) o2);
                return d1.equals(d2);
            }else if (o1.getClass().isArray()){
                //比较两个数组是否相等
                //先比较两个数组的长度
                int array1Length = java.lang.reflect.Array.getLength(o1);
                int array2Length = java.lang.reflect.Array.getLength(o2);
                if (array1Length != array2Length){
                    return false;
                }else {
                    for (int i = 0;i<array1Length;i++){
                        Object value1 = java.lang.reflect.Array.get(o1,i);
                        Object value2 = java.lang.reflect.Array.get(o2,i);
                        //下面判断语句中用到了递归语句
                        if (!CheckUtil.isEqual(value1,value2)){
                            return false;
                        }
                    }
                    return true;
                }
            }else{
                return false;
            }
        }
    }

    /**
     * 裁剪字符串的头尾空格
     * @param value
     * @return
     */
    private static String _trim(String value) {
        if (value != null) {
            value = value.trim();
        }
        return value;
    }
}
