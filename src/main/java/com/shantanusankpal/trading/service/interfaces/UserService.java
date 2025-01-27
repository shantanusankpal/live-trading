package com.shantanusankpal.trading.service.interfaces;

import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import org.springframework.security.core.userdetails.User;


public interface UserService {

    public UserDao findUserProfileByJwt(String jwt) throws Exception;

    public UserDao findUserByEmail(String email) throws Exception;

    public UserDao findUserById(Long userId) throws Exception;

    public UserDao enableTwoFA(VerificationType verificationType, UserDao userDao);

    public UserDao updatePassword(UserDao userDao, String newPassword);
}
