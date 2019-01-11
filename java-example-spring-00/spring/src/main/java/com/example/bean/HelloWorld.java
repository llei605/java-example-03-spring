package com.example.bean;

public class HelloWorld {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HelloWorld() {
        System.out.println("HelloWorld is Constructor...");
    }

    public HelloWorld(String name) {
        this.name = name;
    }

    public void hello() {
        System.out.println("hello " + this.name);
    }

}
