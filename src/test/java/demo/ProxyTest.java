package demo;

import entity.Man;
import entity.People;

public class ProxyTest {

    public static void main(String[] args) {
        ProxyHandler handler = new ProxyHandler();
        People people = (People) handler.bind(new Man());
        people.setId(11);
        people.setName("天才");
        people.setWork("无业");
        System.out.println(people);
    }

}
