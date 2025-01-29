package com.shantanusankpal.trading.repository;

import com.shantanusankpal.trading.dao.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String> {

    ForgotPasswordToken findByUserDaoId(Long id);

}
