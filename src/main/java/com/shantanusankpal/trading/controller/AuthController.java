package com.shantanusankpal.trading.controller;

import com.shantanusankpal.trading.config.JwtUtils;
import com.shantanusankpal.trading.dao.ForgotPasswordToken;
import com.shantanusankpal.trading.dao.TwoFAOtp;
import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import com.shantanusankpal.trading.dto.AuthResponse;
import com.shantanusankpal.trading.dto.ForgotPasswordTokenRequest;
import com.shantanusankpal.trading.dto.ResetPasswordRequest;
import com.shantanusankpal.trading.dto.VerificationCode;
import com.shantanusankpal.trading.repository.UserRepository;
import com.shantanusankpal.trading.service.EmailService;
import com.shantanusankpal.trading.service.UserDetailsService;
import com.shantanusankpal.trading.service.interfaces.ForgotPasswordService;
import com.shantanusankpal.trading.service.interfaces.TwoFAOtpService;
import com.shantanusankpal.trading.service.interfaces.UserService;
import com.shantanusankpal.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    TwoFAOtpService twoFAOtpService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDao userDao) throws Exception {

        UserDao isPresent = userRepository.findByemail(userDao.getEmail());
        if(isPresent!=null){
            throw new Exception("Email alredy present");
        }

        UserDao newUser = new UserDao();
        newUser.setEmail(userDao.getEmail());
        newUser.setPassword(userDao.getPassword());
        newUser.setFullName(userDao.getFullName());
        UserDao savedUser = userRepository.save(newUser);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDao.getEmail(),
                        userDao.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthResponse authResponse = new AuthResponse();

        authResponse.setJwt(jwtUtils.generateTokenFromEmail(userDao));
        authResponse.setStatus(true);
        authResponse.setMessage("Registered Successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody UserDao userDao) throws Exception {

        String password = userDao.getPassword();
        String userEmail = userDao.getEmail();
        UserDao authUser = userRepository.findByemail(userEmail);

        Authentication auth = authenticate(userEmail,password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtUtils.generateTokenFromEmail(userDao);

        if(userDao.getTwoFactoAuth().isEnabled()){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two Factor Authentication is enabled");
            res.setTwoFAEnabled(true);

            String otp = OtpUtils.generateOtp();

            TwoFAOtp oldOtp = twoFAOtpService.findByUser(authUser.getId());
            if(oldOtp!=null){
                twoFAOtpService.deleteTwoFA(oldOtp);
            }
            TwoFAOtp newOtp = twoFAOtpService.createTwoFAotp(authUser,otp,jwt);

            emailService.sendVerificationOtpEmail(userEmail,otp);

            res.setSession(newOtp.getId());
            return new ResponseEntity<>(res, HttpStatus.OK);

        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("Logged in Successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userEmail, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username");
        }
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }

    @GetMapping("/userDetails/{id}")
    public ResponseEntity<UserDao> getDetails(@PathVariable Long id){
        UserDao userDao = userRepository.getReferenceById(id);

        return new ResponseEntity<>(userDao, HttpStatus.OK);
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp, @RequestParam String id) throws Exception {
        TwoFAOtp twoFAOtp = twoFAOtpService.findById(id);

        if(twoFAOtpService.verifyTwoFA(twoFAOtp,otp))
        {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor authentication verified");
            res.setTwoFAEnabled(true);
            res.setJwt(twoFAOtp.getJwt());

            return  new ResponseEntity<>(res, HttpStatus.OK);
        }
        throw new Exception("Invalid Otp");
    }

    @PostMapping("/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenRequest req) throws Exception {

        UserDao userDao = userService.findUserByEmail(req.getSendTo());

        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findByUser(userDao.getId());

        if(forgotPasswordToken==null){
            forgotPasswordToken = forgotPasswordService.createToken(userDao,id, otp, req.getVerificationType(), req.getSendTo());
        }

        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(userDao.getEmail(), otp);
        }

        AuthResponse response = new AuthResponse();
        response.setSession(forgotPasswordToken.getId());
        response.setMessage("Forgot Password otp sent successfully"+forgotPasswordToken.getOtp());


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/users/reset-password/verify-otp/{otp}")
    public ResponseEntity<String > resetPassword(@RequestParam String id, @RequestBody ResetPasswordRequest req) throws Exception {

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);


        boolean isVerified = forgotPasswordToken.getId().equals(req.getOtp());
        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUserDao(),req.getPassword());
            return new ResponseEntity<>("Password Updated Successfully", HttpStatus.OK);
        }
        else
            throw new RuntimeException("Reset Password Code could not be verified");

    }

}
