package com.example.ITSSJP1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BasePage<T>{
    private int totalPages;
    private long totalElements;
    private int elements;
    private List<T> data;
}
