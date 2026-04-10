package com.example.remi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter // <---
@Setter // <---
public class MemoDTO {
    private Long id;
    private String writer;
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
