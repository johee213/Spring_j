package com.example.lms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonDTO {
    private Long id;
    private String title;
    private String videoUrl;
    private Integer sequence;

    private Long courseId; // 어느 강의에 속하는지
}
