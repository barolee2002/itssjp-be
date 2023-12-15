package com.example.ITSSJP1.controller;

import com.example.ITSSJP1.dto.BasePage;
import com.example.ITSSJP1.dto.PlanDTO;
import com.example.ITSSJP1.dto.Response;
import com.example.ITSSJP1.exception.AppException;
import com.example.ITSSJP1.service.PlanService;
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
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;
    @PostMapping("")
    public Response<PlanDTO> create(@RequestBody PlanDTO planDTO) throws AppException {
        return new Response<>(HttpStatus.CREATED.value(), planService.create(planDTO) );
    }
    @PutMapping("/{planId}")
    public Response<PlanDTO> update( @RequestBody PlanDTO planDTO, @PathVariable int planId){
        return new Response<>(HttpStatus.OK.value(), planService.update(planId, planDTO));
    }
    @DeleteMapping("/{planId}")
    public Response<Integer> delete(@PathVariable Integer planId){
        return new Response<>(HttpStatus.OK.value(), planService.delete(planId));
    }


    @GetMapping("/{userId}")
    public Response<BasePage<PlanDTO>> get(@RequestParam( required = false, defaultValue = Constant.PAST_DATE) String fromDate,
                                             @RequestParam(required = false, defaultValue = Constant.FUTURE_DATE) String toDate,
                                             @RequestParam(required = false, defaultValue = "0") long min,
                                             @RequestParam( required = false, defaultValue = "10000000000") long max,
                                             @RequestParam( required = false, defaultValue = "") String category,
                                             @RequestParam( required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                @PathVariable Integer userId){
        return new Response<>(HttpStatus.OK.value(), planService.get( userId, fromDate, toDate, min, max, category, page, pageSize));

    }

    @GetMapping("/{userId}/category")
    public Response<List<String>> getAllCategories(@PathVariable Integer userId){
        return new Response<>(HttpStatus.OK.value(), planService.getAllCategory(userId));
    }

}
