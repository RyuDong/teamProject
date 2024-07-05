package com.green.jhjh.member.form;

import com.green.jhjh.member.constant.Gender;
import com.green.jhjh.member.constant.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinForm {

    private Integer memberCode;

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력하세요.")
    private String password;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email
    private String email;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력값입니다.")
    private String tel;

    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birthday;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String address;

    @NotBlank(message = "별명은 필수 입력값입니다.")
    private String nickname;

    private Role role;

}
