package com.example.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;      // 차시 제목
    private String videoUrl;   // 영상 URL
    private Integer sequence;  // 정렬 순서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;     // 연관 관계 설정

    private String fileName;
    private String fileOrigin;
}
