package com.example.ITSSJP1.service;

import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.PlanDTO;

public interface PlanService {
    PlanDTO create(PlanDTO planDTO);

    PlanDTO update(Integer planId, PlanDTO planDTO);

    Integer delete(Integer planId);

    BasePage<PlanDTO> get( Integer userId,String fromDate, String toDate, long min, long max, int page, int pageSize);
}
