package com.example.ITSSJP1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDTO {
    private Integer plannerId;
    private Integer userId;
    private String time;
    private long amount;
    private String category;
    private String name;
}
