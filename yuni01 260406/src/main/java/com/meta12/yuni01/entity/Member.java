package com.meta12.yuni01.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//엔티티 설계 = 테이블 설계
@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "memberNo")
    private Long id;

    @Column(name = "username", insertable = true, updatable = false, length = 50, nullable = false, unique = true)
    private String username;

    //@Column(length = 255, nullable = false)
    private String password;

    @Transient //데이터베이스 테이블에는 필드로 생성되지 않게 할 때.
    private String passwordChk;

    private String name;

    //@Column(name = "ssn", insertable = true, updatable = false, length = 50, nullable = false, unique = true)
    private String ssn;

    private String phone;
    private String email;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String grade;
}
