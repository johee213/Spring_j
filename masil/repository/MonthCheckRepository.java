package com.example.masil.repository;

import com.example.masil.entity.MonthCheck;
import com.example.masil.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MonthCheckRepository extends JpaRepository<MonthCheck, Long> {

    List<MonthCheck> findBySiteUser(SiteUser user);
    Optional<MonthCheck> findBySiteUserAndCheckDateBetween(SiteUser user, LocalDateTime start, LocalDateTime end);
    List<MonthCheck> findBySiteUser_Id(Long id);
}
