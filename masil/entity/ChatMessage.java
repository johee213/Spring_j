package com.example.masil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userQuestion; // 어르신이 하신 질문

    @Column(columnDefinition = "TEXT")
    private String aiResponse;   // 챗봇이 한 답변

    private LocalDateTime createdAt;



}