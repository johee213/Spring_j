package com.example.masil.entity;

import com.example.masil.config.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 강사 아이디(PK)

    @Column(nullable = false)
    private String category; // 담당 과목

    @Column(nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Column(nullable = false)
    private String birth;

    private String note; // 비고(자격 사항)

    @Enumerated(EnumType.STRING)
    private Role role;


}
