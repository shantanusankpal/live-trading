package com.shantanusankpal.trading.service;

import com.shantanusankpal.trading.dao.TwoFAOtp;
import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.repository.TwoFAOtpRepository;
import com.shantanusankpal.trading.service.interfaces.TwoFAOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFAOtpServiceImpl implements TwoFAOtpService {


    @Autowired
    TwoFAOtpRepository twoFAOtpRepository;
    @Override
    public TwoFAOtp createTwoFAotp(UserDao userDao, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();

        String id = uuid.toString();
        TwoFAOtp twoFAOtp = new TwoFAOtp();
        twoFAOtp.setOtp(otp);
        twoFAOtp.setId(id);
        twoFAOtp.setJwt(jwt);
        twoFAOtp.setUserDao(userDao);

        return twoFAOtp;
    }

    @Override
    public TwoFAOtp findByUser(Long userId) {
        return twoFAOtpRepository.findByUserDaoId(userId);
    }

    @Override
    public TwoFAOtp findById(String id) {
        Optional<TwoFAOtp> otp = twoFAOtpRepository.findById(id);
        return otp.orElse(null);
    }

    @Override
    public boolean verifyTwoFA(TwoFAOtp twoFAOtp, String otp) {
        return twoFAOtp.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFA(TwoFAOtp twoFAOtp) {
        twoFAOtpRepository.delete(twoFAOtp);

    }
}
