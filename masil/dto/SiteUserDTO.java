package com.example.masil.dto;

import com.example.masil.config.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SiteUserDTO {

    private Long id; //기본키

    @Size(min = 3, max =25, message = "사용자ID 글자수는 3-25 입니다.")
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username; //아이디

    @NotEmpty(message = "이름은 필수항목입니다.")
    private String name;//이름

    @NotEmpty(message = "패스워드는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "패스워드 확인은 필수항목입니다.")
    private String passwordChk;

    @NotEmpty(message = "전화번호 항목은 필수항목입니다.")
    private String phone;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String birth; // int -> String으로 변경!

    private int stampCount; // String -> int로 변경! (도장 개수는 숫자니까요)

//    private String category;

    private Role role;
}
