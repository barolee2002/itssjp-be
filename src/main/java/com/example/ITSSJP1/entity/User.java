package com.example.ITSSJP1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table( name = "user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String fullName;
    private String email;
    private String address;
    private String avatarUrl;
    private long total;
    private String password;
}
