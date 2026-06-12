package com.example.masil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QuizBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SiteUser siteUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_pay_id", nullable = false)
    private OrderPay orderPay;

    // 여기에 카테고리 분류와 과목명을 저장. join 용도
    private Long categoryId; // 카테고리 id
    private String categoryName; // 카테고리 title

    // 참 잘했어요 도장, 이수증을 위한 상태 필드
    private boolean corrected; // 퀴즈 정답 맞춤 여부 (true가 되면 다 들었다는 의미!)

    private LocalDateTime successDate; // 강의를 다 들은 날(=도장 찍힌 날)

    private boolean stamp; // 도장 찍기 위한 필드

    private int score;

    public void updateStamp() {
        // 2개 이상 맞으면 도장(true)
        this.stamp = (this.score >= 2);
    }


}