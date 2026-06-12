package com.example.masil.entity;
import com.example.masil.config.Role;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;

@Entity
    @Getter
    @Setter
    public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키

    @Column(unique = true, nullable = false, updatable = false)
        private String username; //아이디

        private String name;//이름

        @Column(nullable = false)
        private String password;

        private String phone;

         private int stampCount;

        @Column(nullable = false)
        private String birth;

        @Enumerated(EnumType.STRING)
        private Role role;

//        @Column(nullable = true) // 초기 가입 시 과목이 없을 수 있으므로 true
//        private String category; // 👈 [추가] 수강 중인 과목 (예: "키오스크")

        // SiteUser.java 엔티티 파일
        @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
        private List<Question> questionList;

        @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
        private List<Answer> answerList;

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private List<OrderPay> orderPayList;

    // Stamp와 연결된 리스트가 있다면
    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL) // 👈 여기 'siteUser'가 위 필드명과 일치해야 함!
    private List<Stamp> stampList;
    }
