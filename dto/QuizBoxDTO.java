package com.example.masil.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class QuizBoxDTO {

    private Long categoryId;
    private String categoryName;
    private Long contentId;             // 보던 영상 위치 복귀용 ID
    private List<Long> quizIds;         // 화면에서 넘어온 퀴즈 3개의 ID 리스트
    private List<String> userAnswers;   // 사용자가 제출한 3개의 답 리스트

}