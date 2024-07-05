package com.green.jhjh.member.dto;

import com.green.jhjh.member.constant.Gender;
import com.green.jhjh.member.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDto {
    private Integer memberCode;
    private String id;
    private String password;
    private String email;
    private String name;
    private String tel;
    private Gender gender;
    private LocalDate birthday;
    private String address;
    private String nickname;
    private Role role;
}
