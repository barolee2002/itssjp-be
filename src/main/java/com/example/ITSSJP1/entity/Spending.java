package com.example.ITSSJP1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table( name = "spending")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spending {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int spendingId;
    private int userId;
    private String time;
    private long amount;
    private String category;
    private String name;

}
