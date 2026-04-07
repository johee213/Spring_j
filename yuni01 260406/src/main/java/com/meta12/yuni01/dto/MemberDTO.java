package com.meta12.yuni01.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private Long id;
    private String username;
    private String password;
    private String passwordChk;
    private String name;
    private String ssn;
    private String phone;
    private String email;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String grade;
}
