package com.example.masil.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class CommunityDTO {
    private LocalDateTime createdAt;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}