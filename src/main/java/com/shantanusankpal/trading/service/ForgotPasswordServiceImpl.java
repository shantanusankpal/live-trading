package com.shantanusankpal.trading.service;

import com.shantanusankpal.trading.dao.ForgotPasswordToken;
import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import com.shantanusankpal.trading.repository.ForgotPasswordRepository;
import com.shantanusankpal.trading.service.interfaces.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;





    @Override
    public ForgotPasswordToken createToken(UserDao userDao, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();

        forgotPasswordToken.setUserDao(userDao);

        forgotPasswordToken.setSendTo(sendTo);

        forgotPasswordToken.setVerificationType(verificationType);

        forgotPasswordToken.setOtp(otp);

        forgotPasswordToken.setId(id);

        return forgotPasswordRepository.saveAndFlush(forgotPasswordToken);
    }

    @Override
    public ForgotPasswordToken findById(String id) {

        Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);
        return token.orElse(null);

    }

    @Override
    public ForgotPasswordToken findByUser(Long id) {
        return forgotPasswordRepository.findByUserDaoId(id);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {

        forgotPasswordRepository.delete(token);

    }
}
