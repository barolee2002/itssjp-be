package com.example.ITSSJP1.simulator;

import com.example.ITSSJP1.dto.IncomeDTO;
import com.example.ITSSJP1.entity.Income;

public class IncomeObject {
    public static Income getEntity(){
        return Income.builder()
                .incomeId(1)
                .userId(1)
                .time("2023-02-02")
                .amount(100000L)
                .category("salary")
                .build();
    }
    public static IncomeDTO getDTO(){
        return IncomeDTO.builder()
                .incomeId(1)
                .userId(1)
                .amount(100000L)
                .category("salary")
                .time("2023-02-02")
                .build();
    }
}
