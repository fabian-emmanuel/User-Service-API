package com.aneeque.codechallenge.repository;

import com.aneeque.codechallenge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
