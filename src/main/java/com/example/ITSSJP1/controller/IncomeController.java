package com.example.ITSSJP1.controller;

import com.example.ITSSJP1.dto.IncomeDTO;
import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.IncomeStatistic;
import com.example.ITSSJP1.dto.Response;
import com.example.ITSSJP1.exception.AppException;
import com.example.ITSSJP1.service.IncomeService;
import com.example.ITSSJP1.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/income")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;
    @PostMapping("")
    public Response<IncomeDTO> create(@RequestBody IncomeDTO incomeDTO) throws AppException {
        return new Response<>(HttpStatus.CREATED.value(), incomeService.create(incomeDTO) );
    }
    @PutMapping("/{incomeId}")
    public Response<IncomeDTO> update( @RequestBody IncomeDTO incomeDTO, @PathVariable Integer incomeId){
        return new Response<>(HttpStatus.OK.value(), incomeService.update(incomeId, incomeDTO));
    }
    @DeleteMapping("/{incomeId}")
    public Response<Integer> delete(@PathVariable Integer incomeId){
        return new Response<>(HttpStatus.OK.value(), incomeService.delete(incomeId));
    }


    @GetMapping("/{userId}")
    public Response<BasePage<IncomeDTO>> get(@RequestParam( required = false, defaultValue = Constant.PAST_DATE) String fromDate,
                                             @RequestParam(required = false, defaultValue = Constant.FUTURE_DATE) String toDate,
                                             @RequestParam(required = false, defaultValue = "0") long min,
                                             @RequestParam( required = false, defaultValue = "10000000000") long max,
                                             @RequestParam( required = false, defaultValue = "") String category,
                                             @RequestParam( required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize,
                                             @PathVariable Integer userId){
        return new Response<>(HttpStatus.OK.value(), incomeService.get( userId, fromDate, toDate, min, max,category, page, pageSize));

    }

    @GetMapping(value = "/{userId}/statistic")
    public Response<List<IncomeStatistic>> statistic(@PathVariable Integer userId){
        return new Response<>(HttpStatus.OK.value(),incomeService.statistic(userId));
    }

    @GetMapping( "/detail/{incomeId}")
    public Response<IncomeDTO> getDetail( @PathVariable Integer incomeId){
        return new Response<>(HttpStatus.OK.value(), incomeService.detail(incomeId));
    }


}
