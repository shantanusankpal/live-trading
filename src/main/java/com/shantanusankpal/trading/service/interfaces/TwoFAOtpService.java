package com.shantanusankpal.trading.service.interfaces;

import com.shantanusankpal.trading.dao.TwoFAOtp;
import com.shantanusankpal.trading.dao.UserDao;

public interface TwoFAOtpService {
    TwoFAOtp createTwoFAotp(UserDao userDao, String otp, String jwt);

    TwoFAOtp findByUser(Long userId);

    TwoFAOtp findById(String id);

    boolean verifyTwoFA(TwoFAOtp twoFAOtp, String otp);

    void deleteTwoFA(TwoFAOtp twoFAOtp);
}
