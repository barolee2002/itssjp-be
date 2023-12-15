package com.example.ITSSJP1.simulator;

import com.example.ITSSJP1.dto.UserDTO;
import com.example.ITSSJP1.entity.User;

public class UserObject {
    public static User getEntity(){
        return User.builder()
                .id(1)
                .email("vp@gmail.com")
                .total(100000L)
                .address("TH")
                .userName("phuctv")
                .password("password")
                .fullName("fullName")
                .avatarUrl("http://localhost:8080/image")
                .build();
    }
    public static UserDTO getDTO(){
        return UserDTO.builder()
                .id(1)
                .email("vp@gmail.com")
                .total(100000L)
                .address("TH")
                .userName("phuctv")
                .fullName("fullName")
                .avatarUrl("http://localhost:8080/image")
                .build();
    }
}
