package com.example.controller;

import com.example.bean.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/person")
public class PersonController {

    @PostMapping("/add")
    public Person add(@RequestBody Person person) {

        return person;
    }
}
