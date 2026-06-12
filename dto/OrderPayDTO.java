package com.example.masil.dto;


import com.example.masil.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class OrderPayDTO {

    private Long id; //기본키
    private Category category; //카테고리
    private String price; //결제금액
    private LocalDateTime payday; // 결제날짜
    private String payType; //결제수단 (ex 카드 )
    private Long siteUserId;
    private String cardNumber;// 카드번호
    private String instructorName;//강사이름

}
