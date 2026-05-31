package com.example.blog.dto;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class QuestionDTO {

    private Long id;

    private String subject;

    private  String content;

    private String createDate;
}
