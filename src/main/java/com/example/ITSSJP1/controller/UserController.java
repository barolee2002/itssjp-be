package com.example.ITSSJP1.controller;

import com.example.ITSSJP1.config.security.JwtService;
import com.example.ITSSJP1.dto.*;
import com.example.ITSSJP1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    @GetMapping(value = "/statistic/{userId}")
    public Response<Statistic> getStatistic(@PathVariable Integer userId) {
        return new Response<>(HttpStatus.OK.value(), userService.statistic(userId));
    }

    @PostMapping(value = "")
    public Response<UserDTO> signup(@RequestBody UserDTO userDTO) {
        return new Response<>(HttpStatus.CREATED.value(), userService.addUser(userDTO));
    }

    @PostMapping(value = "/login")
    public Response<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new Response<>(HttpStatus.OK.value(), userService.login(loginRequest));
    }

    @PutMapping(value = "/{userId}")
    public Response<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable Integer userId) {
        return new Response<>(HttpStatus.OK.value(), userService.update(userDTO, userId));
    }
    @GetMapping( value = "/{userId}")
    public Response<UserDTO> getInfo(@PathVariable Integer userId){
        return new Response<>(HttpStatus.OK.value(), userService.getInfo(userId));
    }

}
