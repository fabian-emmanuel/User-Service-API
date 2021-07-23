package com.aneeque.codechallenge.service;

import com.aneeque.codechallenge.dto.UserDTO;
import com.aneeque.codechallenge.model.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    void createUser(UserDTO userDTO);

    List<User> getAllUsers();
}
