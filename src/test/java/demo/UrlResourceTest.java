package demo;


import com.google.common.io.CharStreams;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class UrlResourceTest {

    public static void main(String[] args) throws Exception {
        UrlResource url = new UrlResource("https://hanyuanqiang.github.io/");
        System.out.println("getFilename():"+url.getFilename());
        System.out.println("getDescription():"+url.getDescription());

        String text1 = CharStreams.toString(new InputStreamReader(url.getInputStream(), "UTF-8"));
        System.out.println(text1);
        System.out.println("===========================");
        String text2 = IOUtils.toString(url.getInputStream(),"UTF-8");
        System.out.println(text2);
        System.out.println("-----------------------");
        System.out.println(text1.equals(text2));
        System.out.println();
        System.out.println("-------------------");

        URI uri = url.getURI();
        System.out.println(uri.getPath());
        System.out.println(uri.getPort());
        System.out.println(uri.getAuthority());
        System.out.println(uri.getHost());
        System.out.println(uri.getQuery());
        System.out.println(uri.getFragment());
    }

}
