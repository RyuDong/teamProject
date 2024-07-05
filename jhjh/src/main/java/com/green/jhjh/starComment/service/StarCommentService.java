package com.green.jhjh.starComment.service;

import com.green.jhjh.member.dto.MemberDto;
import com.green.jhjh.member.mapper.MemberMapper;
import com.green.jhjh.starComment.dto.CommentDto;
import com.green.jhjh.starComment.dto.StarPointDto;
import com.green.jhjh.starComment.form.StarCommentForm;
import com.green.jhjh.starComment.mapper.StarCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class StarCommentService {

    @Autowired
    private StarCommentMapper starCommentMapper;

    @Autowired
    private MemberMapper memberMapper;

    public int getStarAverage(int contentCode) {
        return starCommentMapper.getStarAverage(contentCode);
    }

    public int getMemberCode(String id){
        MemberDto member = starCommentMapper.getMember(id);

        return member.getMemberCode();
    }

    public void overlapStar(Map map) {
        StarPointDto starPointDto = starCommentMapper.overlapStar(map);

        if(starPointDto != null) {
            throw new IllegalStateException("한 콘텐츠 당 별점 평가는 1회만 가능합니다.");
        }
    }

    public int createStarPoint(String id, StarCommentForm starCommentForm) {
        MemberDto member = starCommentMapper.getMember(id);

        StarPointDto starPointDto = new StarPointDto();
        starPointDto.setStarPointValue(starCommentForm.getStarPointValue());

        Map map = new HashMap();
        map.put("memberCode", member.getMemberCode());
        map.put("contentCode", starCommentForm.getContentCode());
        map.put("starPointValue", starPointDto.getStarPointValue());

        starCommentMapper.insertStarPoint(map);

        return starCommentForm.getContentCode();
    }

    public int getStarPointCode(Map map) {
        StarPointDto starPointDto = starCommentMapper.overlapStar(map);
        return starPointDto.getStarPointCode();
    }

    public void createComment(int starPointCode, int contentCode, StarCommentForm starCommentForm) {
            Map map = new HashMap();
            map.put("commentText",starCommentForm.getCommentText());
            map.put("starPointCode", starPointCode);
            map.put("contentCode", contentCode);

            starCommentMapper.insertComment(map);
    }

    public List<CommentDto> getComments(Map map){
        return starCommentMapper.getComments(map);
    }

    public int countComments(Map map){
        return starCommentMapper.countComments(map);
    }

    public List<CommentDto> getMyComments(Map map) {
        return starCommentMapper.getMyComments(map);
    }

    public int countMyComment(Map map){
        return starCommentMapper.countMyComment(map);
    }

    public void deleteComment(int commentCode) {
        starCommentMapper.deleteComment(commentCode);
    }

    public CommentDto getMyComment(int commentCode){
        return starCommentMapper.getMyComment(commentCode);
    }

    public int updateComment(Map map){
         return starCommentMapper.updateComment(map);
    }

    public void validateComment(int commentCode, String id){
        CommentDto commentDto = starCommentMapper.getMyComment(commentCode);

        if(!commentDto.getId().equals(id)) {
            throw new IllegalStateException("작성자에게만 권한이 있습니다.");
        }

    }



}
