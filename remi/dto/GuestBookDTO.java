package com.example.remi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestBookDTO {
    private Long id;
    private String name;
    private String email;
    private String passwd;
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
