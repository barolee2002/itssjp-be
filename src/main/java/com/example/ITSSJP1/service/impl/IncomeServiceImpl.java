package com.example.ITSSJP1.service.impl;

import com.example.ITSSJP1.dto.IncomeDTO;
import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.IncomeStatistic;
import com.example.ITSSJP1.entity.Income;
import com.example.ITSSJP1.entity.User;
import com.example.ITSSJP1.exception.AppException;
import com.example.ITSSJP1.exception.Errors;
import com.example.ITSSJP1.repository.IncomeRepository;
import com.example.ITSSJP1.repository.UserRepository;
import com.example.ITSSJP1.service.IncomeService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl  implements IncomeService {
    private final IncomeRepository incomeRepo;
    private final UserRepository userRepo;
    @Override
    public IncomeDTO create(IncomeDTO incomeDTO) throws AppException{
        ModelMapper mapper = new ModelMapper();
        if( Objects.isNull(incomeDTO.getUserId()))
            throw new AppException(Errors.INVALID_DATA);
        Optional<User> userOptional = userRepo.findById(incomeDTO.getUserId());
        if (userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        User user = userOptional.get();
        Income income = mapper.map(incomeDTO, Income.class);
        income.setTime(Utils.getDate(Constant.DATE_FORMAT));
        Income savedIncome = incomeRepo.save(income);
        user.setTotal(user.getTotal() + savedIncome.getAmount());
        userRepo.save(user);
        return mapper.map(savedIncome, IncomeDTO.class);
    }

    @Override
    public IncomeDTO update(Integer incomeId, IncomeDTO incomeDTO) {
        ModelMapper mapper = new ModelMapper();
        if(Objects.isNull(incomeDTO)|| Objects.isNull(incomeDTO.getUserId()) || Objects.isNull(incomeDTO.getIncomeId()))
            throw new AppException(Errors.INVALID_DATA);
        Optional<Income> incomeOptional = incomeRepo.findById(incomeDTO.getIncomeId());
        if(incomeOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        Optional<User> userOptional = userRepo.findById(incomeDTO.getUserId());
        if (userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        User user = userOptional.get();
        Income currentIncome = incomeOptional.get();
        user.setTotal(user.getTotal() - currentIncome.getAmount() + incomeDTO.getAmount());
        userRepo.save(user);
        Income newIncome = mapper.map(incomeDTO, Income.class);
        newIncome.setTime(Utils.getDate(Constant.DATE_FORMAT));
        return mapper.map(incomeRepo.save(newIncome), IncomeDTO.class);
    }

    @Override
    public Integer delete(Integer incomeId) {
        Optional<Income> incomeOptional = incomeRepo.findById(incomeId);
        if (incomeOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        Income income = incomeOptional.get();
        User user = userRepo.findById(income.getUserId()).get();
        user.setTotal(user.getTotal() - income.getAmount());
        userRepo.save(user);
        incomeRepo.delete(income);
        return incomeId;
    }

    @Override
    public BasePage<IncomeDTO> get(Integer userId, String fromDate, String toDate, long min, long max, String category, int page, int pageSize) {
        if ( !Utils.isCorrectFormat(fromDate, Constant.DATE_FORMAT) || !Utils.isCorrectFormat(toDate, Constant.DATE_FORMAT))
            throw new AppException(Errors.INCORRECT_FORMAT);
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Income> incomePage = incomeRepo.findAll( userId,fromDate, toDate, min - 1, max + 1, category , pageable);
        BasePage<IncomeDTO> dataPage = new BasePage<>();
        dataPage.setTotalElements(incomePage.getTotalElements());
        dataPage.setTotalPages(incomePage.getTotalPages());
        dataPage.setElements(incomePage.getNumberOfElements());
        ModelMapper mapper = new ModelMapper();
        dataPage.setData(incomePage.get().map(income -> mapper.map(income, IncomeDTO.class)).collect(Collectors.toList()));
        return dataPage;
    }

    @Override
    public List<IncomeStatistic> statistic(Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if( userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        List<Object[]> data = incomeRepo.statisticByUserId(userId);
        List<IncomeStatistic> incomeStatistics = new ArrayList<>();
        for ( Object[] row : data){
            IncomeStatistic incomeStatistic = IncomeStatistic.builder()
                    .category(row[0] != null? (String)row[0] : Constant.OTHER)
                    .amount(((BigDecimal)row[1]).longValue())
                    .build();
            incomeStatistics.add(incomeStatistic);
        }
        return incomeStatistics;
    }

    @Override
    public IncomeDTO detail(Integer incomeId) {
        Optional<Income> incomeOptional = incomeRepo.findById(incomeId);
        if( incomeOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        return new ModelMapper().map(incomeOptional.get(), IncomeDTO.class);
    }


}
