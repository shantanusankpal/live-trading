package com.shantanusankpal.trading.service.interfaces;

import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import com.shantanusankpal.trading.dto.VerificationCode;

public interface VerificationCodeService {

    public VerificationCode generateVerificationCode(UserDao userDao, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id);

    VerificationCode gerVerifictionCodeByUser(Long userId);

    void deleteVerificationCode(VerificationCode verificationCode);

    Boolean verifyOtp(String otp, VerificationCode verificationCode);


}
