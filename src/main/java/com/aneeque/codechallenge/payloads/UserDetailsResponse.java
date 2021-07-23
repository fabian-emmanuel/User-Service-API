package com.aneeque.codechallenge.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
