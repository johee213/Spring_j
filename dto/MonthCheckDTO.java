package com.example.masil.dto;


import com.example.masil.entity.SiteUser;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString(exclude = "siteUser")
public class MonthCheckDTO {
    private Long id;


    private SiteUser siteUser;

    private LocalDateTime checkDate;

}
