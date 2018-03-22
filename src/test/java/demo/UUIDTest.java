package demo;

import com.hyq.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class UUIDTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        UUID uuid=UUID.randomUUID();
        System.out.println(uuid.toString());

        System.out.println("-------------------");
        String hexStr = StringUtil.str2Hex("你猜我画");
        System.out.println(hexStr);
        System.out.println(StringUtil.hex2Str(hexStr));
    }

}
