package com.example.remi.entity;

import com.example.remi.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name="no", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(updatable = false, nullable = false, unique = true)
    private String username;

    private String password;
    private String name;
    private String email;

    @Column(updatable = false, nullable = false, unique = true)
    private String ssn;

    private String address1;
    private String address2;
    private String address3;
    private String address4;

    @Enumerated(EnumType.STRING)
    private Role role; // 권한
}
