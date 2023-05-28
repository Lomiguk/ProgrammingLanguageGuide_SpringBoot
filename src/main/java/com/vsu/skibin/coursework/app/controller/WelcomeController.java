package com.vsu.skibin.coursework.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"","/", "/welcome"})
public class WelcomeController {
    @GetMapping
    public String hello(){
        return "redirect:swagger-ui/index.html";
    }
}
