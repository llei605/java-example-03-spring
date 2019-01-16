package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "This is the index page.";
    }

    @GetMapping("/login")
    public String login() {
        return "This is the login page.";
    }

    @GetMapping("/admin")
    public String admin() {
        return "This is the admin page.";
    }
}