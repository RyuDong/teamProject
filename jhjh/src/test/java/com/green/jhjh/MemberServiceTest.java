package com.green.jhjh;

import com.green.jhjh.member.service.MemberSecurityService;
import com.green.jhjh.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSecurityService memberSecurityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("로그인 테스트")
    public void loginMemberTest() throws Exception {
        String memberId = "admin";
        String memberPw = "12345678";

        mockMvc.perform(formLogin().userParameter("memberId")
                .loginProcessingUrl("/member/login")
                .user(memberId).password(memberPw))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());

        mockMvc.perform(MockMvcRequestBuilders.get("/member/logout"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}
