package com.example.masil.config;


import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반회원"),
    INSTRUCTOR("ROLE_INSTRUCTOR", "강사");


    Role(String value) {
        this.value = value;
    }

    private String value;
    private String description;

    Role(String value, String description) {
        this.value = value;
        this.description = description;
    }
}