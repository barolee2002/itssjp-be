package com.example.ITSSJP1.service;

import com.example.ITSSJP1.dto.IncomeDTO;
import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.IncomeStatistic;
import com.example.ITSSJP1.exception.AppException;

import java.util.List;

public interface IncomeService {
    IncomeDTO create(IncomeDTO incomeDTO) throws AppException;

    IncomeDTO update(Integer incomeId, IncomeDTO incomeDTO);

    Integer delete(Integer incomeId);

    BasePage<IncomeDTO> get(Integer userId, String fromDate, String toDate, long min, long max, String category, int page, int pageSize);

    List<IncomeStatistic> statistic(Integer userId);

    IncomeDTO detail(Integer incomeId);

    List<String> getAllCategories(Integer userId);
}
