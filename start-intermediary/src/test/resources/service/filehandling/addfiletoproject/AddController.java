package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddController {

    @GetMapping("/add")
    public int add(@RequestParam(value = "num1") int num1, @RequestParam(value = "num2") int num2) {
        return num1 + num2;
    }
}