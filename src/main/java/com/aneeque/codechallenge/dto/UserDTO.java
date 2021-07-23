package com.aneeque.codechallenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
