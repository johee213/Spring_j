package com.example.sbb.dto;

import com.example.sbb.entity.Question;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerDTO {
    private Long id;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;

    private LocalDateTime createDate;

    private Long questionId;
}
