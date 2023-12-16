package com.example.ITSSJP1.service.impl;

import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.SpendingDTO;
import com.example.ITSSJP1.dto.SpendingStatistic;
import com.example.ITSSJP1.entity.Spending;
import com.example.ITSSJP1.entity.User;
import com.example.ITSSJP1.exception.AppException;
import com.example.ITSSJP1.exception.Errors;
import com.example.ITSSJP1.repository.SpendingRepository;
import com.example.ITSSJP1.repository.UserRepository;
import com.example.ITSSJP1.service.SpendingService;
import com.example.ITSSJP1.utils.Constant;
import com.example.ITSSJP1.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpendingServiceImpl implements SpendingService {
    private final SpendingRepository spendingRepo;
    private final UserRepository userRepo;

    @Override
    public SpendingDTO create(SpendingDTO spendingDTO) {
        ModelMapper mapper = new ModelMapper();
        if( !Utils.isCorrectFormat(spendingDTO.getTime(), Constant.DATE_FORMAT))
            throw new AppException(Errors.INCORRECT_FORMAT);
        if(Objects.isNull(spendingDTO.getUserId()))
            throw new AppException(Errors.INVALID_DATA);
        User user = userRepo.findById(spendingDTO.getUserId()).orElseThrow(()-> new AppException(Errors.INVALID_DATA));

        Spending spending = mapper.map(spendingDTO, Spending.class);
        Spending savedSpending = spendingRepo.save(spending);
        user.setTotal(user.getTotal() - savedSpending.getAmount());
        userRepo.save(user);
        return mapper.map(savedSpending, SpendingDTO.class);
    }

    @Override
    public SpendingDTO update(Integer spendingId, SpendingDTO spendingDTO) {
        ModelMapper mapper = new ModelMapper();
        if( !Utils.isCorrectFormat(spendingDTO.getTime(), Constant.DATE_FORMAT))
            throw new AppException(Errors.INCORRECT_FORMAT);
        if( Objects.isNull(spendingDTO.getUserId()))
            throw new AppException(Errors.INVALID_DATA);
        Spending spending = spendingRepo.findById(spendingDTO.getSpendingId()).orElseThrow( ()-> new AppException(Errors.INVALID_DATA));
        User user = userRepo.findById(spendingDTO.getUserId()).orElseThrow(()-> new AppException(Errors.INVALID_DATA));
        Spending newSpending = mapper.map(spendingDTO, Spending.class);
        user.setTotal(user.getTotal() - newSpending.getAmount() + spending.getAmount());
        return mapper.map(spendingRepo.save(newSpending), SpendingDTO.class);
    }

    @Override
    public Integer delete(Integer spendingId) {
        Optional<Spending> spendingOptional = spendingRepo.findById(spendingId);
        if( spendingOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        Spending spending = spendingOptional.get();
        User user = userRepo.findById(spending.getUserId()).orElseThrow(()-> new AppException(Errors.INVALID_DATA));
        user.setTotal(user.getTotal() + spending.getAmount());
        userRepo.save(user);
        spendingRepo.delete(spending);
        return spendingId;
    }

    @Override
    public BasePage<SpendingDTO> get(Integer userId, String fromDate, String toDate, long min, long max, String category, int page, int pageSize) {
        if ( !Utils.isCorrectFormat(fromDate, Constant.DATE_FORMAT) || !Utils.isCorrectFormat(toDate, Constant.DATE_FORMAT))
            throw new AppException(Errors.INCORRECT_FORMAT);
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Spending> spendingPage = spendingRepo.findAll(userId, fromDate, toDate, min - 1, max + 1, category, pageable);
        BasePage<SpendingDTO> dataPage = new BasePage<>();
        dataPage.setTotalPages(spendingPage.getTotalPages());
        dataPage.setElements(spendingPage.getNumberOfElements());
        dataPage.setTotalElements(spendingPage.getTotalElements());
        ModelMapper mapper = new ModelMapper();
        dataPage.setData(spendingPage.get().map(spending -> mapper.map(spending, SpendingDTO.class)).toList());
        return dataPage;
    }

    @Override
    public List<SpendingStatistic> statistic(Integer userId) {
        if( userRepo.findById(userId).isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        List<Object[]> data = spendingRepo.statistic(userId);
        List<SpendingStatistic> spendingStatistics= new ArrayList<>();
        for( Object[] row : data){
            SpendingStatistic spendingStatistic = SpendingStatistic.builder()
                    .category(row[0] != null ? (String)row[0] : Constant.OTHER)
                    .amount(((BigDecimal)row[1]).longValue())
                    .build();
            spendingStatistics.add(spendingStatistic);
        }
        return spendingStatistics;
    }

    @Override
    public SpendingDTO getDetail(Integer spendingId) {
        Optional<Spending> spendingOptional = spendingRepo.findById(spendingId);
        if( spendingOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        return new ModelMapper().map(spendingOptional.get(), SpendingDTO.class);
    }
}
