package com.hyq.util;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供一些常用的与字符串相关的工具方法
 */
public class StringUtil {

    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * 删除所有的标点符号
     * @param s 处理的字符串
     */
    public  static String removeAllPunct(String s) {
        if(CheckUtil.isEmpty(s)){
            return "";
        }
        return s.replaceAll("[\\pP\\p{Punct}]", "");
    }

    /**
     * 快速比较俩个字符串的相似度
     * @return 俩个字符串的相似度
     */
    public static double similarDegree(String s1, String s2) {
        s1 = StringUtil.removeAllPunct(s1);
        s2 = StringUtil.removeAllPunct(s2);

        s1 = removeSign(s1);
        s2 = removeSign(s2);

        if (s1.length() > s2.length()) {
            int temp = Math.max(s1.length(), s2.length());
            int temp2 = longestCommonSubstring(s1, s2).length();
            return temp2 * 1.0 / temp;
        } else {
            int temp = Math.max(s2.length(), s1.length());
            int temp2 = longestCommonSubstring(s2, s1).length();
            return temp2 * 1.0 / temp;
        }

    }

    private static String removeSign(String str) {
        StringBuffer sb = new StringBuffer();
        for (char item : str.toCharArray()){
            if (charReg(item)) {
                sb.append(item);
            }
        }
        return sb.toString();
    }

    private static boolean charReg(char charValue) {
        return (charValue >= 0x4E00 && charValue <= 0X9FA5) || (charValue >= 'a' && charValue <= 'z') || (charValue >= 'A' && charValue <= 'Z') || (charValue >= '0' && charValue <= '9');
    }

    /**
     * 获取最长公共子串
     * @param strA
     * @param strB
     * @return
     */
    private static String longestCommonSubstring(String strA, String strB) {
        char[] chars_strA = strA.toCharArray();
        char[] chars_strB = strB.toCharArray();
        int m = chars_strA.length;
        int n = chars_strB.length;
        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (chars_strA[i - 1] == chars_strB[j - 1])
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                else
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
            }
        }
        char[] result = new char[matrix[m][n]];
        int currentIndex = result.length - 1;
        while (matrix[m][n] != 0) {
            if (matrix[n] == matrix[n - 1])
                n--;
            else if (matrix[m][n] == matrix[m - 1][n])
                m--;
            else {
                result[currentIndex] = chars_strA[m - 1];
                currentIndex--;
                n--;
                m--;
            }
        }
        return new String(result);
    }

    /**
     * 判断一个字符串是否满足指定的模式，并且不能超过最大长度
     * @param src   要判断的字符串
     * @param maxLen    最大长度
     * @param pattern   匹配模式
     * @return
     */
    public static boolean matchLengthAndPattern(String src, int maxLen, String pattern) {
        if (src == null){
            return false;
        }
        if (maxLen > 0 && src.length() > maxLen) {
            return false;
        }
        if (pattern != null && !src.matches(pattern)) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否匹配指定模式
     * @param src
     * @param patterns  多个模式用","或";"隔开，如：view/.*,auth/.*,demo/.*
     * @return
     */
    public static boolean matchPatterns(String src, String patterns) {
        if (patterns == null || patterns.length() == 0)
            return false;

        return matchPatterns(src, patterns.split("[,;]"));
    }

    public static boolean matchPatterns(String src, String[] patterns) {
        if(patterns != null && patterns.length > 0)
        {
            for(String pattern : patterns)
            {
                Matcher matcher = Pattern.compile(pattern).matcher(src);
                if(matcher.matches())
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换最后一个匹配的字符串
     * @param src
     * @param oldChar
     * @param newChar
     * @return
     */
    public static String replaceLast(String src, String oldChar, String newChar){
        int index = -1;
        for(int i=index;i<src.length();i++){
            index = src.indexOf(oldChar,index+1);
            if (index != -1){
                i = index;
                i--;
            }else {
                index = i;
                break;
            }
        }
        if (index >=0){
            return src.substring(0,index)+src.substring(index).replace(oldChar,newChar);
        }else{
            return src;
        }
    }

    /**
     * 字符串加密函数MD5实现
     */
    public final static String getMd5(String password){
        MessageDigest md;
        try {
            // 生成一个MD5加密计算摘要
            md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(password.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String pwd = new BigInteger(1, md.digest()).toString(16);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    /**
     * 把字符串转为16进制
     * @param str
     * @return
     */
    public static String str2Hex(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder("");
        int bit;
        for (int i = 0; i < bytes.length; i++) {
            bit = (bytes[i] & 0x0f0) >> 4;
            sb.append(hexArray[bit]);
            bit = bytes[i] & 0x0f;
            sb.append(hexArray[bit]);
        }
        return sb.toString().trim();
    }

    /**
     * 把16进制转为字符串
     * @param hex
     * @return
     */
    public static String hex2Str(String hex) {
        String str = "0123456789ABCDEF";
        char[] hexs = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
    
    /**
     * 因为String 带的replaceAll方法支持正则，所以有时候不方便
     * 这个方法则不支持正则，只是单纯替换所有符合的字符串
     * @param content
     * @param oldSub
     * @param newSub
     * @return
     */
    public static String replaceAll(String content,String oldSub,String newSub){
        while (content.indexOf(oldSub)>0){
            content = content.replace(oldSub,newSub);
        }
        return content;
    }
    
    //异或加密
    public static String XOREncrypt(String content) throws Exception {
        String key = PropUtil.getValue(Constants.XOR_CRYPTO_KEY);
        String separator = PropUtil.getValue(Constants.XOR_CRYPTO_SEPARATOR);
        StringBuffer result = new StringBuffer();  //存储加密后的字符串
        //加密过程
        for(int i=0;i<content.length();i++) {
            char KEY = key.charAt(i%key.length());
            int num = content.charAt(i) ^ KEY;
            if (i == content.length()-1){
                result.append(num);
            }else{
                result.append(num+separator);
            }
        }
        return result.toString();
    }
    
    //异或解密
    public static String XORDecrypt(String content) throws Exception{
        String key = PropUtil.getValue(Constants.XOR_CRYPTO_KEY);
        String separator = PropUtil.getValue(Constants.XOR_CRYPTO_SEPARATOR);
        StringBuffer result = new StringBuffer();
        String temp = "";
        int tag = 0;
        for (int i=0;i<content.length();i++){
            if (CheckUtil.isEqual(String.valueOf(content.charAt(i)),separator)){
                char KEY = key.charAt(tag%key.length());
                char c = (char)(Integer.parseInt(temp) ^ KEY);
                result.append(c);
                temp = "";
                tag++;
            }else{
                temp += String.valueOf(content.charAt(i));
            }
        }
        return result.toString();
    }
}
