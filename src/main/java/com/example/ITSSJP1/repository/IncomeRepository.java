package com.example.ITSSJP1.repository;

import com.example.ITSSJP1.entity.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    @Query( nativeQuery = true, value = "SELECT * FROM income i WHERE " +
            " (i.user_id = :userId) " +
            " AND ( STR_TO_DATE(i.time, '%Y-%m-%d') BETWEEN STR_TO_DATE(:fromDate , '%Y-%m-%d') AND STR_TO_DATE(:toDate, '%Y-%m-%d')) " +
            " AND ( i.amount BETWEEN :min AND :max) ")
    Page<Income> findAll(Integer userId, String fromDate, String toDate, long min, long max, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM income where user_id = :userId")
    List<Income> findByUserId(int userId);
    @Query( nativeQuery = true, value = "SELECT category, sum(amount) amount from income" +
            " where( year(str_to_date(time, '%Y-%m-%d')) = year(curdate()) and month( str_to_date( time, '%Y-%m-%d')) = month(curdate()) and user_id = :userId)" +
            " group by category")
    List<Object[]> statisticByUserId(Integer userId);

    Income findFirstByCategory(String s);
}
