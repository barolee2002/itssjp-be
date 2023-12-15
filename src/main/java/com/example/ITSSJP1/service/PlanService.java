package com.example.ITSSJP1.service;

import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.PlanDTO;

import java.util.List;

public interface PlanService {
    PlanDTO create(PlanDTO planDTO);

    PlanDTO update(Integer planId, PlanDTO planDTO);

    Integer delete(Integer planId);

    BasePage<PlanDTO> get(Integer userId, String fromDate, String toDate, long min, long max, String category, int page, int pageSize);

    List<String> getAllCategory(Integer userId);
}
