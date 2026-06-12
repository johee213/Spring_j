package com.example.masil.dto;

import com.example.masil.entity.Answer;
import com.example.masil.entity.SiteUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class QuestionDTO {

    private Long id;

    @NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;

    private LocalDateTime createDate;
    private List<Answer> answerList;
    private SiteUser author;

    private LocalDateTime createdAt;

}
