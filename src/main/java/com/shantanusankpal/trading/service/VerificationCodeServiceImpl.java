package com.shantanusankpal.trading.service;

import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import com.shantanusankpal.trading.dto.VerificationCode;
import com.shantanusankpal.trading.repository.VerificationCodeRepository;
import com.shantanusankpal.trading.service.interfaces.VerificationCodeService;
import com.shantanusankpal.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {



    @Autowired
    VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode generateVerificationCode(UserDao userDao, VerificationType verificationType) {

        VerificationCode newVerificationCode = new VerificationCode();

        newVerificationCode.setUserDao(userDao);

        newVerificationCode.setOtp(OtpUtils.generateOtp());

        newVerificationCode.setVerificationType(verificationType);

        verificationCodeRepository.saveAndFlush(newVerificationCode);
        return newVerificationCode;
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) {

        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);

        if(verificationCode.isEmpty())
            throw  new RuntimeException("Verification Code not found");

        return verificationCode.get();
    }

    @Override
    public VerificationCode gerVerifictionCodeByUser(Long userId) {

        return verificationCodeRepository.findByUserDaoId(userId);

    }

    @Override
    public void deleteVerificationCode(VerificationCode verificationCode) {

        verificationCodeRepository.delete(verificationCode);

    }

    @Override
    public Boolean verifyOtp(String otp, VerificationCode verificationCode) {
        return  otp.equals(verificationCode.getOtp());
    }
}
