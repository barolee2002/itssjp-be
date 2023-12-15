package com.example.ITSSJP1.controller;

import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.Response;
import com.example.ITSSJP1.dto.SpendingDTO;
import com.example.ITSSJP1.dto.SpendingStatistic;
import com.example.ITSSJP1.service.SpendingService;
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
@RequestMapping("/spending")
@RequiredArgsConstructor
public class SpendingController {
    private final SpendingService spendingService;

    @PostMapping("")
    public Response<SpendingDTO> create(@RequestBody SpendingDTO spendingDTO){
        return new Response<>(HttpStatus.CREATED.value(), spendingService.create(spendingDTO));
    }
    @PutMapping("/{spendingId}")
    public Response<SpendingDTO> update(@RequestBody SpendingDTO spendingDTO, @PathVariable Integer spendingId){
        return new Response<>(HttpStatus.OK.value(), spendingService.update(spendingId, spendingDTO));
    }
    @DeleteMapping("/{spendingId}")
    public Response<Integer> delete(@PathVariable Integer spendingId){
        return new Response<>(HttpStatus.OK.value(), spendingService.delete(spendingId));
    }

    @GetMapping("/{userId}")
    public Response<BasePage<SpendingDTO>> get(@RequestParam( required = false, defaultValue = Constant.PAST_DATE) String fromDate,
                                             @RequestParam(required = false, defaultValue = Constant.FUTURE_DATE) String toDate,
                                             @RequestParam(required = false, defaultValue = "0") long min,
                                             @RequestParam( required = false, defaultValue = "10000000000") long max,
                                               @RequestParam( required = false, defaultValue = "") String category,
                                             @RequestParam( required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize, @PathVariable Integer userId){
        return new Response<>(HttpStatus.OK.value(), spendingService.get( userId, fromDate, toDate, min, max, category, page, pageSize));

    }
    @GetMapping( "/{userId}/statistic")
    public Response<List<SpendingStatistic>> statistic(@PathVariable Integer userId){
        return new Response<>(HttpStatus.OK.value(), spendingService.statistic(userId));
    }

    @GetMapping( "/detail/{spendingId}")
    public Response<SpendingDTO> getDetail(@PathVariable Integer spendingId){
        return new Response<>(HttpStatus.OK.value(), spendingService.getDetail(spendingId));
    }
}
