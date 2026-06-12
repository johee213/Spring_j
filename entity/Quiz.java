package com.example.masil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lectureId;

    @Column(columnDefinition = "TEXT")
    private String question;

    private String answer;

    private String description;

    private LocalDateTime createDate;

    @Column(nullable = false)
    private Integer score = 0;

    @Column(name = "is_stamp", nullable = false)
    private Boolean isStamp = false;

    @Column(nullable = false)
    private Boolean stamp = false;
}