package com.hyq.test;

import com.hyq.util.StringUtil;

public class StringUtilTest {

    public static void main(String[] args) {
        String str1 = "直播吧3月4日讯 骑士坐镇主场迎战背靠背的掘金，此役掘金外线手感火热，加里-哈里斯、穆雷和巴顿等连中三分，帮助掘金以73-62领先结束上半场。易边再战，詹姆斯在第三节频频送出助攻，JR-史密斯、希尔等连续里-哈里斯等下起一波三分雨，掘金打出11-0拉开比分。最终，掘金在客场以得分，骑士不断把比分迫近。不过，掘金在末节再次找回外线手感，穆雷、米尔萨普和加126-117击败骑士，取得2连胜，同时送给骑士2连败。";
        String str2 = "直播吧3月4-62领先结束上半场。易边再里-哈里斯等下起一波三分雨，掘金打出11-0拉开比分。最终，掘金在迎战背靠背的掘金，此役掘金外线手感火热，加迫近攻，JR-史密斯、希尔等连续得分，骑士不断把比分日讯 骑士坐镇主场雷、米尔萨普和加。不过，掘金在末节再次找回外线手感，穆客场以126-117击败骑士，取得2连胜，同时送给骑士2连败。";
        System.out.println(StringUtil.similarDegree(str1,str2));

        System.out.println("----------");
        System.out.println(StringUtil.getMd5("8235143+"));
    
        System.out.println('_' == '_');
    }

}
