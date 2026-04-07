package com.meta12.yuni01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoDTO {
    private Long id;

    private String subject;
    private String content;
    private String writer;
}
