package com.shantanusankpal.trading.controller;

import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import com.shantanusankpal.trading.service.EmailService;
import com.shantanusankpal.trading.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDao> getUserProfile(@RequestParam("Authorization") String jwt) throws Exception {

        UserDao userDao = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(userDao, HttpStatus.OK);

    }


    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<UserDao> sendVerificationOtp(@RequestParam("Authorization") String jwt, @PathVariable VerificationType verificationType) throws Exception {

        UserDao userDao = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(userDao, HttpStatus.OK);
    }


    @PatchMapping("/api/users/enable-two-fa/verify-otp/{otp}")
    public ResponseEntity<UserDao> enableTowFa(@RequestParam("Authorization") String jwt) throws Exception {
        UserDao userDao = userService.findUserProfileByJwt(jwt);



        return new ResponseEntity<>(userDao, HttpStatus.OK);
    }
}
