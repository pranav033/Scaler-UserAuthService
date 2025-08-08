package org.example.userauthservice.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.dtos.LoginRequestDto;
import org.example.userauthservice.dtos.SignUpRequestDto;
import org.example.userauthservice.dtos.UserDto;
import org.example.userauthservice.dtos.ValidateTokenRequestDto;
import org.example.userauthservice.models.User;
import org.example.userauthservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

//    signup
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto signUpRequestDto)
    {
        User user = authService.signup(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getPhoneNumber());
        UserDto userDto = from(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

//    login
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto)
    {
        Pair<User,String> response = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        UserDto userDto = from(response.a);
        String token = response.b;
        MultiValueMap headers = new LinkedMultiValueMap();
        headers.add(HttpHeaders.SET_COOKIE,token);
        return new ResponseEntity<>(userDto,headers,HttpStatus.OK);
    }

//    validatetoken
    @PostMapping("/validatetoken")
    public ResponseEntity<Boolean> validateToke(@RequestBody ValidateTokenRequestDto validateTokenRequestDto)
    {
        return null;
    }
//    logout --TODO
//    forgetpassword --TODO

    UserDto from(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        return userDto;
    }


}
