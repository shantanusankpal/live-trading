package com.shantanusankpal.trading.service.interfaces;

import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import org.springframework.security.core.userdetails.User;


public interface UserService {

    UserDao findUserProfileByJwt(String jwt) throws Exception;

    UserDao findUserByEmail(String email) throws Exception;

    UserDao findUserById(Long userId) throws Exception;

    UserDao enableTwoFA(VerificationType verificationType, UserDao userDao);

    UserDao updatePassword(UserDao userDao, String newPassword);
}
