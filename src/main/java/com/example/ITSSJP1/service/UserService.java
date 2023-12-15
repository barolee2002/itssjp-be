package com.example.ITSSJP1.service;

import com.example.ITSSJP1.dto.LoginRequest;
import com.example.ITSSJP1.dto.LoginResponse;
import com.example.ITSSJP1.dto.Statistic;
import com.example.ITSSJP1.dto.UserDTORequest;
import com.example.ITSSJP1.dto.UserDTOResponse;

public interface UserService {
    Statistic statistic(Integer userId);

    UserDTOResponse addUser(UserDTOResponse userDTO);

    LoginResponse login(LoginRequest loginRequest);

    UserDTOResponse update(UserDTORequest userDTO, Integer userId);

    UserDTOResponse getInfo(Integer userId);
}
