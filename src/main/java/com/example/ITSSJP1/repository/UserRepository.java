package com.example.ITSSJP1.repository;

import com.example.ITSSJP1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query( nativeQuery = true, value = "select * from user where user_name = :username limit 1")
    Optional<User> findByUserName(String username);
}
