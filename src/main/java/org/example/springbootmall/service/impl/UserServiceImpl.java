package org.example.springbootmall.service.impl;

import org.example.springbootmall.dao.UserDao;
import org.example.springbootmall.dto.UserRequest;
import org.example.springbootmall.model.User;
import org.example.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRequest userRequest) {
        return userDao.createUser(userRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
