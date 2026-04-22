package com.example.lms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {
    private Long id;
    private String title;
    private String instructor;
    private String description;
}
