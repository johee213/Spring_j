package com.meta12.yuni01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestBookDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String content;
}
