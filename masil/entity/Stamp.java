package com.example.masil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Stamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "siteUser_id")
    private SiteUser siteUser; // 필드명을 siteUser로 변경

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private SiteUser teacher;

    private LocalDateTime stampDate;
}