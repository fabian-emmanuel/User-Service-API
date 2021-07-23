package com.aneeque.codechallenge.controller;

import com.aneeque.codechallenge.dto.UserDTO;
import com.aneeque.codechallenge.model.User;
import com.aneeque.codechallenge.payloads.UserDetailsRequest;
import com.aneeque.codechallenge.payloads.UserDetailsResponse;
import com.aneeque.codechallenge.payloads.UserLoginRequest;
import com.aneeque.codechallenge.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDetailsResponse> signUp(@Valid @RequestBody UserDetailsRequest userRequest) {
        var user = Optional.ofNullable(userService.getUserByEmail(userRequest.getEmail()));
        if (user.isEmpty()) {

            String regexToValidateEmail = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regexToValidateEmail);
            Matcher matchEmail = pattern.matcher(userRequest.getEmail());

            if (!matchEmail.matches()) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            UserDTO userDTO = mapper.map(userRequest, UserDTO.class);
            userService.createUser(userDTO);

            UserDetailsResponse userResponse = mapper.map(userDTO, UserDetailsResponse.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } else return new ResponseEntity<>(HttpStatus.IM_USED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpSession session, @Valid @RequestBody UserLoginRequest userRequest) {
        var user = Optional.ofNullable(userService.getUserByEmail(userRequest.getEmail()));
        if (user.isPresent() && user.get().getPassword().equals(userRequest.getPassword())) {
            session.setAttribute("user", user.get());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", user.get().toString());
            return ResponseEntity.ok().headers(headers).body(user.get());
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
