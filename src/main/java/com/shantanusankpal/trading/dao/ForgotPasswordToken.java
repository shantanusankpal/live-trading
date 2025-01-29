package com.shantanusankpal.trading.dao;

import com.shantanusankpal.trading.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class ForgotPasswordToken {

    @Id
    private String id;

    @OneToOne
    private UserDao userDao;

    private String otp;

    private VerificationType verificationType;

    private String sendTo;

}
