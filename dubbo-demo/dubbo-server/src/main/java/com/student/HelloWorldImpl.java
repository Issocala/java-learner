package com.student;

public class HelloWorldImpl implements HelloWorld{
    @Override
    public String  sayHelloWorld(String msg) {
        System.out.println("我是服务端，我被调用了！！！");
        return msg + "调用服务端！！！！";
    }
}
