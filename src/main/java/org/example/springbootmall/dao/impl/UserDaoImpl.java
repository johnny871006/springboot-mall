package org.example.springbootmall.dao.impl;

import org.example.springbootmall.dao.UserDao;
import org.example.springbootmall.dto.UserRequest;
import org.example.springbootmall.model.User;
import org.example.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId) {

        String sql = "SELECT user_id,user_name,email,password,created_date,last_modified_date " +
                "FROM user WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (!userList.isEmpty()) {
            return userList.getFirst();
        } else {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {

        String sql = "SELECT user_id,user_name,email,password,created_date,last_modified_date " +
                "FROM user WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (!userList.isEmpty()) {
            return userList.getFirst();
        } else {
            return null;
        }
    }

    @Override
    public Integer createUser(UserRequest userRequest) {

        String sql = "INSERT INTO user(user_name,email,password,created_date,last_modified_date) " +
                "VALUES(:userName,:email,:password,:createDate,:lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userName", userRequest.getUserName());
        map.put("email", userRequest.getEmail());
        map.put("password", userRequest.getPassword());

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;

    }
}
