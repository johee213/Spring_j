package com.example.masil.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString(exclude = "siteUser")
public class MonthCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser siteUser;

    private LocalDateTime checkDate;

}
