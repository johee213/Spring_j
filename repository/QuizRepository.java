package com.example.masil.repository;

import com.example.masil.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // 🌟 @Query를 사용해 권한 세션과 상관없이 DB에서 lectureId 매칭 데이터를 강제로 긁어오도록 고정합니다.
    @Query("SELECT q FROM Quiz q WHERE q.lectureId = :lectureId ORDER BY q.id ASC")
    List<Quiz> findByLectureId(@Param("lectureId") Long lectureId);

}