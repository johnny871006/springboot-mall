package org.example.springbootmall.dao;

import org.example.springbootmall.dto.UserRequest;
import org.example.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRequest userRequest);
}
