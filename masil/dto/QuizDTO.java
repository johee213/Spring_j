package com.example.masil.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDTO {
    private Long id;
    private Long lectureId;
    private String question;
    private String answer;
    private String description;
    private int score;
    private boolean stamp;
}