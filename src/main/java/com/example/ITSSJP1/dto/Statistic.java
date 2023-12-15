package com.example.ITSSJP1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Statistic {
    private long incomeTotal;
    private long spendingTotal;
    private long savings;
}
