package com.example.ITSSJP1.service;

import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.SpendingDTO;
import com.example.ITSSJP1.dto.SpendingStatistic;

import java.util.List;

public interface SpendingService {
    SpendingDTO create(SpendingDTO spendingDTO);

    SpendingDTO update(Integer spendingId, SpendingDTO spendingDTO);

    Integer delete(Integer spendingId);

    BasePage<SpendingDTO> get(Integer userId, String fromDate, String toDate, long min, long max, String category, int page, int pageSize);

    List<SpendingStatistic> statistic(Integer userId);

    SpendingDTO getDetail(Integer spendingId);
}
