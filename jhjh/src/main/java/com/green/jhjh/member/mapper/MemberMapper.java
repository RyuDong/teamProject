package com.green.jhjh.member.mapper;

import com.green.jhjh.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    int createMember(MemberDto memberDto);

    String overlapId(String id);

    String overlapNickname(String nickname);

    String overlapEmail(String email);

    MemberDto findMember(String id);

    int updateMember(MemberDto memberDto);
}
