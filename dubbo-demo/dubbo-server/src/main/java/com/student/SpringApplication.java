package com.student;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SpringApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-server.xml");
        context.start();
        System.in.read();
    }
}
