package com.aneeque.codechallenge.repository;

import com.aneeque.codechallenge.dto.UserDTO;
import com.aneeque.codechallenge.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class UserRepoUnitTest {

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    void initUseCase(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(UUID.randomUUID().toString());
        userDTO.setFirstName("Fabian");
        userDTO.setLastName("Emmanuel");
        userDTO.setEmail("admin@admin.com");
        userDTO.setPassword("1234567");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = mapper.map(userDTO, User.class);
        userRepo.save(user);
    }

    @AfterEach
    public void destroyAll() {
        userRepo.deleteAll();
    }

    @Test
    void findUserByEmail_success(){
        User user = userRepo.findUserByEmail("admin@admin.com");
        assertThat(user.getFirstName().equals("Fabian")).isTrue();

    }

}