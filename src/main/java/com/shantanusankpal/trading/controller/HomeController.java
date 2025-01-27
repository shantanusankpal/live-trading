package com.shantanusankpal.trading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "This is home";
    }


    @GetMapping("/api")
    public String secureHome(){
        return "This is Secure home";
    }
}
