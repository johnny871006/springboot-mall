package org.example.springbootmall.service;

import org.example.springbootmall.dto.UserLoginRequest;
import org.example.springbootmall.dto.UserRequest;
import org.example.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRequest userRequest);

    User login(UserLoginRequest userLoginRequest);
}
