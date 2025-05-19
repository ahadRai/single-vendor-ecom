package com.example.singleEcommerce.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, secured world!";
    }
}
