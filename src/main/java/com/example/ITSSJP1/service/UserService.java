package com.example.ITSSJP1.service;

import com.example.ITSSJP1.dto.LoginRequest;
import com.example.ITSSJP1.dto.LoginResponse;
import com.example.ITSSJP1.dto.Statistic;
import com.example.ITSSJP1.dto.UserDTO;

public interface UserService {
    Statistic statistic(Integer userId);

    UserDTO addUser(UserDTO userDTO);

    LoginResponse login(LoginRequest loginRequest);

    UserDTO update(UserDTO userDTO, Integer userId);

    UserDTO getInfo(Integer userId);
}
