package com.shantanusankpal.trading.repository;

import com.shantanusankpal.trading.dao.TwoFAOtp;
import com.shantanusankpal.trading.service.interfaces.TwoFAOtpService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFAOtpRepository extends JpaRepository<TwoFAOtp, String> {
    TwoFAOtp findByUserDaoId(Long userId);
}
