package com.example.remi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String ssn;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String role;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;

    private String passwordChk;
    private String ssn1;
    private String ssn2;
}
