package com.example.ITSSJP1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTORequest {
    private int id;
    private String userName;
    private String fullName;
    private String email;
    private String address;
    private MultipartFile avatarUrl;
    private long total;
    private String password;
}
