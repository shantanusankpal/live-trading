package com.shantanusankpal.trading.dto;

import com.shantanusankpal.trading.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {

    private String sendTo;
    private VerificationType verificationType;
}
