package com.shantanusankpal.trading.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private boolean status;
    private String message;
    private boolean isTwoFAEnabled;
    private String session;
}
