package com.example.ITSSJP1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpendingDTO {
    private int spendingId;
    private Integer userId;
    private String time;
    private long amount;
    private String category;
    private String name;
}
