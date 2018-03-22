package demo;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

public class ClassPathResourceTest {

    public static void main(String[] args) throws Exception{
        ClassPathResource resource = new ClassPathResource("/test.txt");
        System.out.println(resource.exists());
        System.out.println(resource.getDescription());
        System.out.println(resource.getFilename());
        String text = IOUtils.toString(resource.getInputStream(),"UTF-8");
        System.out.println(text);
    }

}
