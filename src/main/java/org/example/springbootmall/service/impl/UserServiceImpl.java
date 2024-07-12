package org.example.springbootmall.service.impl;

import org.example.springbootmall.dao.UserDao;
import org.example.springbootmall.dto.UserRequest;
import org.example.springbootmall.model.User;
import org.example.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
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

        if(user != null){
            logger.warn("此 Email {} 已經註冊過!",userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 註冊
        return userDao.createUser(userRequest);
    }
}
