package org.example.springbootmall.service.impl;

import org.example.springbootmall.dao.UserDao;
import org.example.springbootmall.dto.UserLoginRequest;
import org.example.springbootmall.dto.UserRequest;
import org.example.springbootmall.model.User;
import org.example.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;


@Component
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {

        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRequest userRequest) {

        // 檢查Email
        User user = userDao.getUserByEmail(userRequest.getEmail());
        if (user != null) {
            logger.warn("此 Email {} 已經註冊過!", userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5進行hash密碼加密
        String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());
        userRequest.setPassword(hashedPassword);

        // 註冊
        return userDao.createUser(userRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查Email是否有註冊
        if (user == null) {
            logger.warn("此Email {} 尚未註冊!", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5進行hash密碼加密
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 檢查密碼是否正確
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            logger.warn("此Email {} 的密碼輸入錯誤", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
