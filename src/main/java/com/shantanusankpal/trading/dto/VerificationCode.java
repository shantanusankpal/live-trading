package com.shantanusankpal.trading.dto;

import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;

    @OneToOne
    private UserDao userDao;

    private String email;

    private String mobile;

    private VerificationType verificationType;

}
