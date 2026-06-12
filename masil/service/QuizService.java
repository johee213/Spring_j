package com.example.masil.service;

import com.example.masil.entity.Quiz;
import com.example.masil.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 🌟 추가

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    @Transactional(readOnly = true)
    public List<Quiz> getList() {
        return quizRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Quiz getQuiz(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("퀴즈를 찾을 수 없습니다."));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'ROLE_ADMIN', 'ROLE_INSTRUCTOR')")
    @Transactional
    public void save(Quiz quiz) {
        if (quiz.getId() == null) {
            quiz.setCreateDate(LocalDateTime.now());
        }
        quizRepository.save(quiz);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'ROLE_ADMIN', 'ROLE_INSTRUCTOR')")
    @Transactional
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'ROLE_ADMIN', 'ROLE_INSTRUCTOR')")
    @Transactional
    public void chugaProc(Quiz quiz) {
        quizRepository.save(quiz);
    }

    // 🌟 [핵심 수정]: 일반 유저 세션에서도 시큐리티 프록시 검열에 걸리지 않고
    // 데이터베이스에서 순수하게 데이터를 긁어오도록 읽기 전용 트랜잭션을 강제 부여합니다!
    @Transactional(readOnly = true)
    public List<Quiz> getQuizByLectureId(Long lectureId) {
        List<Quiz> list = quizRepository.findByLectureId(lectureId);
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }


}