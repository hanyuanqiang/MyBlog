package com.hyq.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ImageCodeUtil {
    
    // 图片的宽度。
    public static int default_width = 120;
    // 图片的高度。
    public static int default_height = 40;
    // 验证码字符个数
    public static int default_codeCount = 4;
    // 验证码干扰线数
    public static int default_lineCount = 80;
    /*// 验证码
    private static String code = null;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;*/
    
    private final static char[] codeSequence = {
            'A','B','C','D','E','F','G','H','J','K','L','M',
            'N','P','Q','R','S','T','U','V','W','X','Y','Z',
            '1','2', '3', '4', '5', '6', '7', '8', '9'/*,
            'a','b','c','d','e','f','g','h','i','j','k','l','m',
            'n','o','p','q','r','s','t','u','v','w','x','y','z'*/
    };
    
    public final static Color[] colorArray = {
            Color.decode("#8B0000"),Color.decode("#000000"),Color.decode("#FF0000"),Color.decode("#A0522D"),
            Color.decode("#006400"),Color.decode("#00FF00"),Color.decode("#3CB371"),Color.decode("#008080"),
            Color.decode("#2F4F4F"),Color.decode("#4682B4"),Color.decode("#000080"),Color.decode("#4B0082"),
            Color.decode("#9932CC"),Color.decode("#9400D3"),Color.decode("#C71585")
    };
    
    
    public static Map<String,Object> genImgCode() {
        return genImgCode(default_width,default_height);
    }
    
    public static Map<String,Object> genImgCode(int width, int height) {
        return genImgCode(width,height,default_codeCount);
    }
    
    public static Map<String,Object> genImgCode(int width, int height, int codeCount) {
        return genImgCode(width,height,codeCount,default_lineCount);
    }
    
    public static Map<String,Object> genImgCode(int width, int height, int codeCount, int lineCount) {
        Map<String,Object> result = new HashMap<String, Object>();
        String code = null;
        BufferedImage buffImg = null;
        
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;
        
        x = (width-20) / codeCount;// 每个字符的宽度
        fontHeight = height-14;// 字体的高度
        codeY = (height-5)-(height-14)/4;
        
        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
//        g.setColor(new Color(214,198,173));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体
        ImgFontByte imgFont = new ImgFontByte();
        Font font = imgFont.getFont(fontHeight);
        g.setFont(font);
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
    
            int xe = width;
            int ye = height;
            
            if (random.nextInt(100)%2==0){
                xe = xs + random.nextInt(width / 4);
            }else{
                xe = xs - random.nextInt(width / 4);
            }
            if (random.nextInt(100)%2==0){
                ye = ys + random.nextInt(height / 2);
            }else{
                ye = ys - random.nextInt(height / 2);
            }
            
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }
        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(colorArray[random.nextInt(colorArray.length)]);
            g.drawString(strRand, 10+i * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        code = randomCode.toString();
        g.dispose();
        result.put("imgCode",code);
        result.put("buffImg",buffImg);
        return result;
    }
    
    public static void write(String filePath,BufferedImage bi) throws IOException {
        OutputStream out = new FileOutputStream(new File(filePath));
        ImageIO.write(bi,"png",out);
    }
    
    
    private static class ImgFontByte {
        public Font getFont(int fontHeight){
            try {
                Font baseFont = Font.createFont(Font.ITALIC, new ByteArrayInputStream(hex2byte(getFontByteStr())));
                return baseFont.deriveFont(Font.PLAIN, fontHeight);
            } catch (Exception e) {
                return new Font("Consola",Font.BOLD, fontHeight);
            }
        }
        
        private  byte[] hex2byte(String str) {
            if (str == null)
                return null;
            str = str.trim();
            int len = str.length();
            if (len == 0 || len % 2 == 1)
                return null;
            byte[] b = new byte[len / 2];
            try {
                for (int i = 0; i < str.length(); i += 2) {
                    b[i/2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
                }
                return b;
            } catch (Exception e) {
                return null;
            }
        }
        
        /**
         * ttf字体文件的十六进制字符串
         * @return
         */
        private String getFontByteStr(){
            return null;
        }
    }
}

