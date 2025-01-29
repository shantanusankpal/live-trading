package com.shantanusankpal.trading.dto;


import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String otp;

    private String password;
}
