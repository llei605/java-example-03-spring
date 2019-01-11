package com.example.controller;

import com.example.bean.Person;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "This is the Index Page.";
    }


    /*
    * url: localhost:8080/hello/123?name=tom
    *
    * result: id = 123;  name = tom
    * */
    @GetMapping("/hello/{id}")
    public String hello (@PathVariable(name = "id")String id, @RequestParam(name = "name")String name) {

        String retStr = "id = " + id + ";  name = " + name;

        System.out.println(retStr);
        return retStr;
    }

    /**
     * Url: localhost:8080/greeting
     * Request Json:
     * {
     * 	"username": "aaaaa",
     * 	"password": "111111",
     * 	"age": 12
     * }
     * Result: Person(username=aaaaa, password=111111, age=12)
     * @param person
     * @return
     */
    @PostMapping("/greeting")
    public String greeting(@RequestBody Person person) {

        return person.toString();
    }
}
