package com.example.masil.repository;

import com.example.masil.entity.QuizBox;
import com.example.masil.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizBoxRepository extends JpaRepository<QuizBox, Long> {

    // 특정 수강생(User)이 특정 과목(Category)의 퀴즈를 풀었는지 내역 조회
    Optional<QuizBox> findBySiteUserAndCategoryId(SiteUser siteUser, Long categoryId);

    List<QuizBox> findBySiteUserAndCorrected(SiteUser siteUser, boolean corrected);
}