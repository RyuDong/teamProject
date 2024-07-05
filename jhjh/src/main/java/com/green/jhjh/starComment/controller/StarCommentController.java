package com.green.jhjh.starComment.controller;

import com.green.jhjh.config.PageHandler;
import com.green.jhjh.member.dto.MemberDto;
import com.green.jhjh.member.service.MemberService;
import com.green.jhjh.starComment.dto.CommentDto;
import com.green.jhjh.starComment.dto.StarPointDto;
import com.green.jhjh.starComment.form.StarCommentForm;
import com.green.jhjh.starComment.service.StarCommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StarCommentController {

    @Autowired
    StarCommentService starCommentService;

    @PostMapping("/starComment")
    public ResponseEntity createStarComment(@RequestBody @Valid StarCommentForm starCommentForm,
                                            BindingResult bindingResult, Principal principal) {
        int contentCode;
        try {
            if (principal.getName() != null) {
                String id = principal.getName();

                if (bindingResult.hasErrors()) {
                    StringBuilder sb = new StringBuilder();
                    List<FieldError> fieldErrors = bindingResult.getFieldErrors();

                    for (FieldError fieldError : fieldErrors) {
                        sb.append(fieldError.getDefaultMessage());
                    }

                    return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
                }

                Map map = new HashMap();
                map.put("contentCode", starCommentForm.getContentCode());
                map.put("memberCode", starCommentService.getMemberCode(id));

                try {
                    starCommentService.overlapStar(map);
                    contentCode = starCommentService.createStarPoint(id, starCommentForm);

                    if (starCommentForm.getCommentText() != null) {
                        int starPointCode = starCommentService.getStarPointCode(map);
                        starCommentService.createComment(starPointCode, contentCode, starCommentForm);
                    }
                } catch (Exception e) {
                    return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }
        }catch(NullPointerException e) {
            return new ResponseEntity<String>("로그인 후 이용해주세요.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Integer>(starCommentForm.getContentCode(), HttpStatus.OK);
    }

    @GetMapping(value={"/comment/{contentCode}","/comment/{contentCode}/{page}"})
    public String getComment(@PathVariable("contentCode") Integer contentCode,
                             @PathVariable(value="page", required = false) Integer page, Model model) {
        int ps = 6;
        if(page == null) page = 1;

        Map map = new HashMap();
        map.put("pageSize", ps);
        map.put("page", page * ps - ps);
        map.put("contentCode", contentCode);

        int totalCnt = starCommentService.countComments(map);

        PageHandler pageHandler1 = new PageHandler(totalCnt, ps, page);
        model.addAttribute("pageHandler", pageHandler1);

        List<CommentDto> commentDtoList = starCommentService.getComments(map);
        model.addAttribute("commentList",  commentDtoList);

        return "content/comment";
    }

    @GetMapping(value={"/myComment", "/myComment/{page}"})
    public String getMyComments(@PathVariable(value="page", required = false) Integer page,
                                Model model, Principal principal) {
        int memberCode = starCommentService.getMemberCode(principal.getName());

        int ps = 6;
        if(page ==null) page = 1;

        Map map = new HashMap();
        map.put("memberCode", memberCode);
        map.put("page", page * ps - ps);
        map.put("pageSize", ps);

        int totalCnt = starCommentService.countMyComment(map);

        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);
        model.addAttribute("pageHandler", pageHandler);

        List<CommentDto> commentList = starCommentService.getMyComments(map);
        model.addAttribute("commentList", commentList);
        return "member/myComment";
    }

    //코멘트 수정 페이지 가기
    @GetMapping("/myComment/view/{commentCode}")
    public String commentView(@PathVariable("commentCode") Integer commentCode, Model model){
        CommentDto commentDto = starCommentService.getMyComment(commentCode);
        model.addAttribute("comment", commentDto);
        return "member/updateComment";
    }

    @GetMapping("/myComment/delete/{commentCode}")
    public String deleteComment(@PathVariable("commentCode") Integer commentCode,
                                Principal principal, RedirectAttributes redirectAttributes){
        String id = principal.getName();
        starCommentService.validateComment(commentCode, id);

        starCommentService.deleteComment(commentCode);
        redirectAttributes.addFlashAttribute("successMessage","코멘트가 삭제되었습니다.");

        return "redirect:/myComment";
    }

    @PostMapping("/myComment/update/{commentCode}")
    public String updateComment(@PathVariable("commentCode") Integer commentCode,
                                @RequestParam("commentText") String commentText, Principal principal,
                                RedirectAttributes redirectAttributes){
        String id = principal.getName();
        starCommentService.validateComment(commentCode, id);

        Map map = new HashMap();
        map.put("commentCode", commentCode);
        map.put("commentText", commentText);

        starCommentService.updateComment(map);

        redirectAttributes.addFlashAttribute("successMessage","코멘트가 수정되었습니다.");

        return "redirect:/myComment";
    }

}
