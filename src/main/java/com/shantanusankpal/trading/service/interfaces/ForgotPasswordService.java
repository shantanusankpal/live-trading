package com.shantanusankpal.trading.service.interfaces;

import com.shantanusankpal.trading.dao.ForgotPasswordToken;
import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(UserDao userDao, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long id);

    void deleteToken(ForgotPasswordToken token);
}
