package org.example.springbootmall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootmall.dao.UserDao;
import org.example.springbootmall.dto.UserLoginRequest;
import org.example.springbootmall.dto.UserRequest;
import org.example.springbootmall.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void register_success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Johnny God");
        userRequest.setEmail("johnnygod269@gmail.com");
        userRequest.setPassword("johnnygod269");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.userName", notNullValue()))
                .andExpect(jsonPath("$.Email", equalTo("johnnygod269@gmail.com")))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));

        // 檢查資料庫的密碼不為明碼
        User user = userDao.getUserByEmail(userRequest.getEmail());
        assertNotEquals(user.getPassword(), userRequest.getPassword());
    }

    @Test
    public void register_invalidEmailFormat() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Johnny God");
        userRequest.setEmail("johnnyGod");
        userRequest.setPassword("johnnyGod");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void register_existEmail() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Johnny God");
        userRequest.setEmail("johnnyGod@gmail.com");
        userRequest.setPassword("johnnyGod");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));

        // 再次使用同一個Email註冊
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_success() throws Exception {
        // 先註冊
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Ning");
        userRequest.setEmail("ning@gmail.com");
        userRequest.setPassword("ning");

        register(userRequest);

        //再登入
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("ning@gmail.com");
        userLoginRequest.setPassword("ning");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.userName", equalTo(userRequest.getUserName())))
                .andExpect(jsonPath("$.Email", equalTo(userRequest.getEmail())))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Test
    public void login_invalidEmailFormat() throws Exception {
        // 先註冊
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Johnny God");
        userRequest.setEmail("johnnygod@gmail.com");
        userRequest.setPassword("johnnygod");

        register(userRequest);

        //再登入
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("johnnygod");
        userLoginRequest.setPassword("johnnygod");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_wrongPassword() throws Exception {
        // 先註冊
        UserRequest userRequest = new UserRequest();
        userRequest.setUserName("Davic");
        userRequest.setEmail("david@gmail.com");
        userRequest.setPassword("david");

        register(userRequest);

        //再登入
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("david@gmail.com");
        userLoginRequest.setPassword("daviddd");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void login_emailNotExist() throws Exception {

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("johnnygod2699@gmail.com");
        userLoginRequest.setPassword("johnnygod2699");

        String json = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    public void register(UserRequest userRequest) throws Exception {
        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }
}