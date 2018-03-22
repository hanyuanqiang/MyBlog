package demo;

import com.hyq.entity.User;
import com.hyq.util.GetterUtil;

import java.lang.reflect.Field;
import java.util.Calendar;

public class FieldTest {

    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setNickName("天才");
        user.setAccount("principle");
        user.setAvatar("头像图片地址");
        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field: fields){
            field.setAccessible(true);
            System.out.println(field.getName()+" : "+field.get(user));
        }

        System.out.println(Calendar.getInstance().get(Calendar.AM));
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        System.out.println("------------------");
        System.out.println(GetterUtil.getUUID());

    }

}
