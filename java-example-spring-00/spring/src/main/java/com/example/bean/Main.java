package com.example.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {

        // 1. 创建spring的ioc容器对象
        System.out.println("Step 1. ...");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");


        // 2. 从ioc容器对象中回去bean实例
        System.out.println("Step 2. ...");
        HelloWorld helloWorld = (HelloWorld) context.getBean("helloWorld");


        // 3. 调用hello方法
        System.out.println("Step 3. ...");
        helloWorld.hello();

    }

}
