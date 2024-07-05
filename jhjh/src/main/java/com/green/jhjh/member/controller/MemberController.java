package com.green.jhjh.member.controller;

import com.green.jhjh.member.dto.MemberDto;
import com.green.jhjh.member.form.JoinForm;
import com.green.jhjh.member.form.UpdateForm;
import com.green.jhjh.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.sf.jsqlparser.statement.select.Join;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/new")
    public String joinForm(Model model){
        model.addAttribute("joinForm", new JoinForm());
        return "member/join";
    }

    @PostMapping("/new")
    public String newMember(@Valid JoinForm joinForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()) {
            return "member/join";
        }

        try{
            memberService.createMember(joinForm);
            redirectAttributes.addFlashAttribute("successMessage", "회원가입을 환영합니다.");
        } catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/join";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/login";
    }

    @GetMapping("/info")
    public String myInfo(Principal principal, Model model) {
        try {
            MemberDto member = memberService.findMember(principal.getName());
            JoinForm joinForm = memberService.makeForm(member);
            model.addAttribute("joinForm", joinForm);
            model.addAttribute("updateForm", new UpdateForm());
        } catch(NullPointerException e) {
            model.addAttribute("errorMessage", "존재하지 않는 회원입니다.");
            return "/";
        }
        return "member/myInfo";
    }

    @PostMapping("/update")
    public String updateInfo(@Validated UpdateForm updateForm, BindingResult bindingResult,
                             Model model, Principal principal, RedirectAttributes redirectAttributes) {
        MemberDto member = memberService.findMember(principal.getName());
        JoinForm joinForm = memberService.makeForm(member);
        model.addAttribute("joinForm", joinForm);

        if(bindingResult.hasErrors()) {
            return "member/myInfo";
        }

        try{
            memberService.updateMember(principal.getName(), updateForm);
            redirectAttributes.addFlashAttribute("successMessage",
                    "회원정보가 성공적으로 변경되었습니다.");
        } catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/myInfo";
        }

        return "redirect:/";
    }
}
