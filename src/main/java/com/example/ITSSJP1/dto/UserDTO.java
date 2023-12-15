package com.example.ITSSJP1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private int id;
    private String userName;
    private String fullName;
    private String email;
    private String address;
    private String avatarUrl;
    private long total;
    private String password;
}
