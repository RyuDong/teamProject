package com.green.jhjh.member.service;

import com.green.jhjh.member.constant.Role;
import com.green.jhjh.member.dto.MemberDto;
import com.green.jhjh.member.form.JoinForm;
import com.green.jhjh.member.form.UpdateForm;
import com.green.jhjh.member.mapper.MemberMapper;
import net.sf.jsqlparser.statement.select.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int createMember(JoinForm joinForm) {
        //Form을 Dto로 변환
        MemberDto memberDto = makeMember(joinForm);

        //중복검사
        overlapId(memberDto.getId());
        overlapEmail(memberDto.getEmail());
        overlapNickname(memberDto.getNickname());

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodedPassword);

        //멤버 생성
        return  memberMapper.createMember(memberDto);
    }

    public void overlapId(String memberId) {
        String findId = memberMapper.overlapId(memberId);

        if(findId != null) {
            throw new IllegalStateException("중복된 아이디입니다.");
        }
    }

    public void overlapNickname(String nickname) {
        String findNickname = memberMapper.overlapNickname(nickname);

        if(findNickname != null) {
            throw new IllegalStateException("중복된 닉네임입니다.");
        }
    }

    public void overlapEmail(String email) {
        String findEmail = memberMapper.overlapEmail(email);

        if(findEmail != null) {
            throw new IllegalStateException("중복된 이메일입니다.");
        }
    }

    public MemberDto makeMember(JoinForm joinForm) {
        MemberDto memberDto = new MemberDto();

        memberDto.setId(joinForm.getId());
        memberDto.setPassword(joinForm.getPassword());
        memberDto.setEmail(joinForm.getEmail());
        memberDto.setName(joinForm.getName());
        memberDto.setTel(joinForm.getTel());
        memberDto.setGender(joinForm.getGender());
        memberDto.setBirthday(joinForm.getBirthday());
        memberDto.setAddress(joinForm.getAddress());
        memberDto.setNickname(joinForm.getNickname());

        memberDto.setRole(Role.USER);

        return memberDto;
    }

    public JoinForm makeForm(MemberDto memberDto) {
        JoinForm joinForm = new JoinForm();
        joinForm.setMemberCode(memberDto.getMemberCode());
        joinForm.setId(memberDto.getId());
        joinForm.setPassword(memberDto.getPassword());
        joinForm.setName(memberDto.getName());
        joinForm.setEmail(memberDto.getEmail());
        joinForm.setTel(memberDto.getTel());
        joinForm.setGender(memberDto.getGender());
        joinForm.setBirthday(memberDto.getBirthday());
        joinForm.setAddress(memberDto.getAddress());
        joinForm.setNickname(memberDto.getNickname());
        joinForm.setRole(memberDto.getRole());

        return joinForm;
    }

    public MemberDto findMember(String id){
        return memberMapper.findMember(id);
    }

    public void updateMember(String id, UpdateForm updateForm){
        MemberDto preMember = memberMapper.findMember(id);

        if(preMember == null) {
            throw new NullPointerException("존재하지 않는 회원입니다.");
        }

        String encodedPassword = passwordEncoder.encode(updateForm.getPassword());
        preMember.setPassword(encodedPassword);

        if(updateForm.getEmail() != null && !updateForm.getEmail().isEmpty() ) {
            overlapEmail(updateForm.getEmail());
            preMember.setEmail(updateForm.getEmail());
        }

        if(updateForm.getTel() != null  && !updateForm.getTel().isEmpty()) {
            preMember.setTel(updateForm.getTel());
        }

        if(updateForm.getAddress() != null  && !updateForm.getAddress().isEmpty()  ) {
            preMember.setAddress(updateForm.getAddress());
        }

        if(updateForm.getNickname() != null  && !updateForm.getNickname().isEmpty()) {
            overlapNickname(updateForm.getNickname());
            preMember.setNickname(updateForm.getNickname());
        }

       memberMapper.updateMember(preMember);
    }


}
