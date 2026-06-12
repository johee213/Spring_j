package com.example.masil.dto;

import com.example.masil.entity.Question;
import com.example.masil.entity.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;



@Getter
@Setter
public class AnswerDTO {

    private Long id;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;

    private LocalDateTime createDate;
    private Long questionId;
    private Long siteUserId;

}
