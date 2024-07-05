package com.green.jhjh.member.form;

import com.green.jhjh.member.constant.Gender;
import com.green.jhjh.member.constant.Role;
import jakarta.validation.constraints.Email;
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
public class UpdateForm {

    private Integer memberCode;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력하세요.")
    private String password;

    @Email
    private String email;

    private String tel;

    private String address;

    private String nickname;

}
