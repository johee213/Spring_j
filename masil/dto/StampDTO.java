package com.example.masil.dto;

import com.example.masil.entity.SiteUser;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class StampDTO {

    private Long id;

    private SiteUser student;

    private SiteUser teacher;

    private LocalDateTime stampDate;
}
