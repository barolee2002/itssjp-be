package com.example.ITSSJP1.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO {
    private Integer incomeId;
    private Integer userId;
    private String time;
    private long amount;
    private String category;
    private String name;
}
