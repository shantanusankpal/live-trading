package com.shantanusankpal.trading.repository;

import com.shantanusankpal.trading.dto.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {

    VerificationCode findByUserDaoId(Long id);
}
