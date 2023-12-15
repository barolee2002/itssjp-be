package com.example.ITSSJP1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String username;
    private int id ;
    private String token;
    private long expireTime;
}
