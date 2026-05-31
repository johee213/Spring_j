package com.example.blog.entity;


import com.example.blog.config.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String email;

//    config 안에 role 이넘으로 만들어서 임포트 뜬거 연결함
    @Enumerated(EnumType.STRING)
    private Role role;

//    createDate 자동으로들어감
    @CreationTimestamp
    private Timestamp createDate;






}
