package com.aneeque.codechallenge.service.serviceImpl;

import com.aneeque.codechallenge.dto.UserDTO;
import com.aneeque.codechallenge.model.User;
import com.aneeque.codechallenge.repository.UserRepo;
import com.aneeque.codechallenge.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;


    @Cacheable(cacheNames = "users", key = "#email", unless = "#result == null")
    @Override
    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @CacheEvict(cacheNames = "users", allEntries = true)
    @Override
    public void createUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = mapper.map(userDTO, User.class);
        userRepo.save(user);
        mapper.map(user, UserDTO.class);
    }

    @Cacheable(cacheNames = "users")
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
