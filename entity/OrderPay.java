package com.example.masil.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class OrderPay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키


    @ManyToOne // 카테고리 엔티티와 연결
    @JoinColumn(name = "category_id")
    private Category category;


    private String price; //결제금액

    private LocalDateTime payday; // 결제날짜

    private String cardNumber; // 카드번호

    private String payType;//결제수단 (ex 카드 )
    private String instructorName;//강사이름

//    @ManyToOne(cascade = CascadeType.ALL) // 또는 CascadeType.PERSIST
//    @JoinColumn(name = "user_id")
//    private SiteUser siteUser;


    @ManyToOne
    @JoinColumn(name = "siteUser_id") // DB에서 어떤 컬럼과 연결할지 명시
    private SiteUser siteUser;


}
