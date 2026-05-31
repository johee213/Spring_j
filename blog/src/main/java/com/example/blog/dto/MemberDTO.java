package com.example.blog.dto;

import com.example.blog.config.Role;
import lombok.Getter;
import lombok.Setter;


import java.sql.Timestamp;



@Getter
@Setter
public class MemberDTO {


    private Long id;
    private String username;
    private String password;
    private String email;

    private Role role;


    private Timestamp createDate;






}
