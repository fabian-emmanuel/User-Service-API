package com.aneeque.codechallenge.service.serviceImpl;

import com.aneeque.codechallenge.dto.UserDTO;
import com.aneeque.codechallenge.model.User;
import com.aneeque.codechallenge.repository.UserRepo;
import com.aneeque.codechallenge.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {
    @Mock
    private UserRepo userRepo;
    private UserService userService;

    @BeforeEach
    void initUseCase(){
        userService = new UserServiceImpl(userRepo);
    }


    @Test
    public void createUser_success(){
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(UUID.randomUUID().toString());
        userDTO.setFirstName("Fabian");
        userDTO.setLastName("Emmanuel");
        userDTO.setEmail("admin@admin.com");
        userDTO.setPassword("1234567");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = mapper.map(userDTO, User.class);
        when(userRepo.save(any(User.class))).thenReturn(user);
        User savedUser = userRepo.save(user);
        assertThat(savedUser.getFirstName()).isEqualTo("Fabian");
        assertThat(savedUser.getLastName()).isEqualTo("Emmanuel");
        assertThat(savedUser.getEmail()).isEqualTo("admin@admin.com");
    }

    @Test
    void getAllUsers_success() {
        //when
        userService.getAllUsers();
        //then
        verify(userRepo).findAll();
    }

    @Test
    void getUserByEmail(){
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(UUID.randomUUID().toString());
        userDTO.setFirstName("Fabian");
        userDTO.setLastName("Emmanuel");
        userDTO.setEmail("admin@admin.com");
        userDTO.setPassword("1234567");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = mapper.map(userDTO, User.class);
        //when
        userService.getUserByEmail(user.getEmail());
        //then
        verify(userRepo).findUserByEmail(user.getEmail());
    }

}