package com.shantanusankpal.trading.controller;

import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import com.shantanusankpal.trading.dto.VerificationCode;
import com.shantanusankpal.trading.service.EmailService;
import com.shantanusankpal.trading.service.interfaces.UserService;
import com.shantanusankpal.trading.service.interfaces.VerificationCodeService;
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

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDao> getUserProfile(@RequestParam("Authorization") String jwt) throws Exception {

        UserDao userDao = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(userDao, HttpStatus.OK);

    }


    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestParam("Authorization") String jwt, @PathVariable VerificationType verificationType) throws Exception {

        UserDao userDao = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.gerVerifictionCodeByUser(userDao.getId());

        if(verificationCode==null){
            verificationCodeService.generateVerificationCode(userDao, verificationType);
        }


        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(userDao.getEmail(), verificationCode.getOtp());
        }


        return new ResponseEntity<>("Verification otp sent successfully"+verificationCode.getOtp(), HttpStatus.OK);
    }


    @PatchMapping("/api/users/enable-two-fa/verify-otp/{otp}")
    public ResponseEntity<UserDao> enableTowFa(@RequestParam("Authorization") String jwt, @PathVariable String otp) throws Exception {
        UserDao userDao = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.gerVerifictionCodeByUser(userDao.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?verificationCode.getEmail():verificationCode.getMobile();


        boolean isVerified = verificationCodeService.verifyOtp(otp,verificationCode);

        if(isVerified){
            UserDao newUserDao = userService.enableTwoFA(verificationCode.getVerificationType(),userDao);
            return new ResponseEntity<>(newUserDao, HttpStatus.OK);
        }
        else
            throw new RuntimeException("Verification Code could not be verified");

    }
}
