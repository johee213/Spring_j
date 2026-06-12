package com.example.masil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content; // 👈 이 필드명이 정확히 'content'여야 합니다.

    private boolean completed;
    private LocalDateTime completedAt;

    @ManyToOne
    private SiteUser siteUser; // 👈 이 필드명이 정확히 'siteUser'여야 합니다.

    private Double lastWatchedTime; // 초 단위로 기록 (예: 180.0)
    private Double percentage;      // 0 ~ 100 사이 값

}
