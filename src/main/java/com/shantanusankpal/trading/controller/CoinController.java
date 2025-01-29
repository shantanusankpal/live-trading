package com.shantanusankpal.trading.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shantanusankpal.trading.service.interfaces.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CoinService coinService;



}
