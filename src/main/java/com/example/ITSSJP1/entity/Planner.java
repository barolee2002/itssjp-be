package com.example.ITSSJP1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "planner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planner {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int plannerId;
    private String name;
    private String category;
    private int amount;
    private String time;
    private int userId;

}
