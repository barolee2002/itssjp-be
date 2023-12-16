package com.example.ITSSJP1.repository;

import com.example.ITSSJP1.entity.Planner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Integer> {
    @Query( nativeQuery = true, value = "SELECT * FROM planner i WHERE " +
            " (i.user_id = :userId)" +
            "  AND ( :category is null or :category = '' or i.category = :category)" +
            " AND( STR_TO_DATE(i.time, '%Y-%m-%d') BETWEEN STR_TO_DATE(:fromDate , '%Y-%m-%d') AND STR_TO_DATE(:toDate, '%Y-%m-%d')) " +
            " AND ( i.amount BETWEEN :min AND :max) ")
    Page<Planner> findAll( Integer userId, String fromDate, String toDate, long min, long max, String category, Pageable pageable);
    @Query( value = "SELECT * FROM planner i where i.user_id = :userId", nativeQuery = true)
    List<Planner> getCategory( @Param("userId") int userId);
}
