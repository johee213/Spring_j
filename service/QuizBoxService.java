package com.example.masil.service;

import com.example.masil.entity.OrderPay;
import com.example.masil.entity.QuizBox;
import com.example.masil.entity.SiteUser;
import com.example.masil.repository.QuizBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizBoxService {

    private final QuizBoxRepository quizBoxRepository;

//    @Transactional
//    public void saveQuizSuccess(SiteUser user, OrderPay orderPay, Long categoryId, String categoryName) {
//
//        // 1. 이미 이 과목의 퀴즈 박스가 존재하는지 조회
//        Optional<QuizBox> _quizBox = quizBoxRepository.findBySiteUserAndCategoryId(user, categoryId);
//
//        QuizBox quizBox;
//
//        if (_quizBox.isPresent()) {
//            // 2-A. 기존 데이터가 있으면 꺼내서 업데이트 준비
//            quizBox = _quizBox.get();
//        } else {
//            // 2-B. 데이터가 없으면 새로 생성
//            quizBox = new QuizBox();
//            quizBox.setSiteUser(user);          // 신규 생성일 때만 관계를 맺어줘도 무방함
//            quizBox.setCategoryId(categoryId);
//        }
//
//        // 3. 변경되거나 매번 갱신되어야 하는 데이터들 세팅
//        quizBox.setOrderPay(orderPay);         // 최근 결제 정보로 갱신될 수 있으므로
//        quizBox.setCategoryName(categoryName); // 혹시 카테고리 명이 바뀌었을 수도 있으니 갱신
//
//        quizBox.setCorrected(true);
//        quizBox.setSuccessDate(LocalDateTime.now()); // 수료 날짜는 지금으로 갱신
//        quizBox.setStamp(true);
//
//        // 4. 저장 처리
//        quizBoxRepository.save(quizBox);
//    }

    // QuizBoxService.java
    // QuizBoxService.java
    // QuizBoxService.java
    @Transactional
    public void saveQuizSuccess(SiteUser user, OrderPay orderPay, Long categoryId, String categoryName, int correctCount) {
        // 1. 기존 데이터 조회 또는 생성
        Optional<QuizBox> _quizBox = quizBoxRepository.findBySiteUserAndCategoryId(user, categoryId);
        QuizBox quizBox = _quizBox.orElse(new QuizBox());

        // 2. 데이터 세팅
        quizBox.setSiteUser(user);
        quizBox.setCategoryId(categoryId);
        quizBox.setOrderPay(orderPay);
        quizBox.setCategoryName(categoryName);

        // 3. 점수 및 도장 상태 저장
        quizBox.setScore(correctCount);
        quizBox.setStamp(correctCount >= 2); // 2문제 이상이면 도장 부여

        quizBox.setCorrected(true);
        quizBox.setSuccessDate(LocalDateTime.now());

        quizBoxRepository.save(quizBox);
    }
}