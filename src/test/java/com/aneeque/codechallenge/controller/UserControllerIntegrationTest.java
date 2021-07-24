package com.aneeque.codechallenge.controller;

import com.aneeque.codechallenge.model.User;
import com.aneeque.codechallenge.payloads.UserDetailsRequest;
import com.aneeque.codechallenge.payloads.UserLoginRequest;
import com.aneeque.codechallenge.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    public void testLogin() throws Exception {
        User user = new User();
        user.setId(123L);
        user.setUserId("42");
        user.setLastName("Doe");
        user.setFirstName("Jane");
        user.setEmail("jane.doe@example.org");
        user.setPassword("?");
        when(this.userService.getUserByEmail(anyString())).thenReturn(user);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("jane.doe@example.org");
        userLoginRequest.setPassword("password");
        String content = (new ObjectMapper()).writeValueAsString(userLoginRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(401));
    }
    @Test
    public void testSignUp() throws Exception {
        UserDetailsRequest userDetailsRequest = new UserDetailsRequest();
        userDetailsRequest.setFirstName("Jane");
        userDetailsRequest.setLastName("Doe");
        userDetailsRequest.setEmail("jane.doe@example.org");
        userDetailsRequest.setPassword("password");
        String content = (new ObjectMapper()).writeValueAsString(userDetailsRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(201));

    }
    @Test
    public void testListAllUsers() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));

    }
}

