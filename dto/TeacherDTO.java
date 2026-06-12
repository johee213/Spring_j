package com.example.masil.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDTO {
    private Long id;
    private String category;
    private String teacherName;
    private String loginId;
    private String password;
    private String passwordChk;
    private String phone;
    private String birth;
    private String note;
}