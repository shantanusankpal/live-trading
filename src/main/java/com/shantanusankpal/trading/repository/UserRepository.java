package com.shantanusankpal.trading.repository;

import com.shantanusankpal.trading.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDao, Long> {
    UserDao findByemail(String username);
}
