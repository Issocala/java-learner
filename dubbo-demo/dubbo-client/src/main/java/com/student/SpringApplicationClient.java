package com.student;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationClient {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-client.xml");
        HelloWorld helloWorld = (HelloWorld) context.getBean("helloWorld");
        System.out.println(helloWorld.sayHelloWorld("客户端"));
    }
}
