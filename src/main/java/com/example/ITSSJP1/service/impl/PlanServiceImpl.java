package com.example.ITSSJP1.service.impl;

import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.PlanDTO;
import com.example.ITSSJP1.entity.Planner;
import com.example.ITSSJP1.exception.AppException;
import com.example.ITSSJP1.exception.Errors;
import com.example.ITSSJP1.repository.PlannerRepository;
import com.example.ITSSJP1.repository.UserRepository;
import com.example.ITSSJP1.service.PlanService;
import com.example.ITSSJP1.utils.Constant;
import com.example.ITSSJP1.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlannerRepository  planRepo;
    private final UserRepository userRepo;

    @Override
    public PlanDTO create(PlanDTO planDTO) {
        if( Objects.isNull(planDTO.getUserId()))
            throw new AppException(Errors.INVALID_DATA);
        userRepo.findById(planDTO.getUserId()).orElseThrow(()-> new AppException(Errors.INVALID_DATA));
        if ( !Utils.isCorrectFormat(planDTO.getTime(), Constant.DATE_FORMAT) )
            throw new AppException(Errors.INCORRECT_FORMAT);
        ModelMapper mapper = new ModelMapper();
        Planner plan = mapper.map(planDTO, Planner.class);
        return mapper.map(planRepo.save(plan), PlanDTO.class);
    }

    @Override
    public PlanDTO update(Integer planId, PlanDTO planDTO) {
        if( Objects.isNull(planDTO.getUserId()))
            throw new AppException(Errors.INVALID_DATA);
        if (userRepo.findById(planDTO.getUserId()).isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        Optional<Planner> plannerOptional = planRepo.findById(planId);
        if( plannerOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        if( !Utils.isCorrectFormat( planDTO.getTime(), Constant.DATE_FORMAT))
            throw new AppException(Errors.INCORRECT_FORMAT);
        ModelMapper mapper = new ModelMapper();
        Planner planner = mapper.map(planDTO, Planner.class);
        planner.setPlannerId(planId);
        planner.setUserId(planner.getUserId());
        return mapper.map(planRepo.save(planner), PlanDTO.class);
    }

    @Override
    public Integer delete(Integer planId) {
        Optional<Planner> plannerOptional = planRepo.findById(planId);
        if( plannerOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        planRepo.delete(plannerOptional.get());
        return planId;
    }

    @Override
    public BasePage<PlanDTO> get(Integer userId, String fromDate, String toDate, long min, long max, String category, int page, int pageSize) {
        if ( !Utils.isCorrectFormat(fromDate, Constant.DATE_FORMAT) || !Utils.isCorrectFormat(toDate, Constant.DATE_FORMAT))
            throw new AppException(Errors.INCORRECT_FORMAT);

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Planner> plannerPage = planRepo.findAll( userId,fromDate, toDate, min - 1, max + 1, category, pageable);
        BasePage<PlanDTO> dataPage = new BasePage<>();
        dataPage.setTotalPages(plannerPage.getTotalPages());
        dataPage.setElements(plannerPage.getNumberOfElements());
        dataPage.setTotalElements(plannerPage.getTotalElements());
        ModelMapper mapper = new ModelMapper();
        dataPage.setData(plannerPage.get().map( planner -> mapper.map(planner, PlanDTO.class)).toList());
        return dataPage;
    }

    @Override
    public List<String> getAllCategory(Integer userId) {
        List<Planner> planners = planRepo.getCategory(userId);
        return planners.stream().map(Planner::getCategory).distinct().toList();
    }
}
