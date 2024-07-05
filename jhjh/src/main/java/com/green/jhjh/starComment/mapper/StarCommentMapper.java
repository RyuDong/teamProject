package com.green.jhjh.starComment.mapper;

import com.green.jhjh.member.dto.MemberDto;
import com.green.jhjh.starComment.dto.CommentDto;
import com.green.jhjh.starComment.dto.StarPointDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StarCommentMapper {
    int getStarAverage(int contentCode);

    MemberDto getMember(String id);

    int insertStarPoint(Map map);

    int insertComment(Map map);

    StarPointDto overlapStar(Map map);

    List<CommentDto> getComments(Map map);

    int countComments(Map map);

    List<CommentDto> getMyComments(Map map);

    int countMyComment(Map map);

    int deleteComment(int commentCode);

    CommentDto getMyComment(int commentCode);

    int updateComment(Map map);
}
