package com.example.ITSSJP1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table( name = "income")
@Entity
@Data
public class Income {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int incomeId;
    private int userId;
    private String time;
    private long amount;
    private String category;
    private String name;
}
