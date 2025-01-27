package com.shantanusankpal.trading.service;

import com.shantanusankpal.trading.config.JwtUtils;
import com.shantanusankpal.trading.dao.UserDao;
import com.shantanusankpal.trading.domain.VerificationType;
import com.shantanusankpal.trading.dto.TwoFactoAuth;
import com.shantanusankpal.trading.repository.UserRepository;
import com.shantanusankpal.trading.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;


    ;@Override
    public UserDao findUserProfileByJwt(String jwt) throws Exception {
        String email = jwtUtils.getUserEmailFromJwtToken(jwt);

        UserDao userDao = userRepository.findByemail(email);

        if(userDao == null){
            throw new Exception("User Not found!");
        }
        return userDao;
    }

    @Override
    public UserDao findUserByEmail(String email) throws Exception {
        UserDao userDao = userRepository.findByemail(email);

        if(userDao == null){
            throw new Exception("User Not found!");
        }
        return userDao;
    }

    @Override
    public UserDao findUserById(Long userId) throws Exception {
        Optional<UserDao> userDao = userRepository.findById(userId);
        if(userDao.isEmpty()){
            throw new Exception("User not found");
        }
        return userDao.get();
    }

    @Override
    public UserDao enableTwoFA(VerificationType verificationType,UserDao userDao) {

        UserDao user = userRepository.findByemail(userDao.getEmail());

        TwoFactoAuth twoFactoAuth = new TwoFactoAuth();

        twoFactoAuth.setEnabled(true);
        twoFactoAuth.setVerificationType(verificationType);

        user.setTwoFactoAuth(twoFactoAuth);

        return userRepository.saveAndFlush(userDao);

    }

    @Override
    public UserDao updatePassword(UserDao userDao, String newPassword) {
        userDao.setPassword(newPassword);
        return userRepository.saveAndFlush(userDao);
    }
}
