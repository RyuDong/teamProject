package com.green.jhjh.contentDtl.controller;

import com.green.jhjh.contentDtl.dto.ContentDtlDto;
import com.green.jhjh.contentDtl.service.ContentDtlService;
import com.green.jhjh.contentManager.dto.ContentImgDto;
import com.green.jhjh.contentManager.dto.CreatorDto;
import com.green.jhjh.contentManager.dto.HolderDto;
import com.green.jhjh.contentManager.service.ContentService;
import com.green.jhjh.starComment.service.StarCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ContentDtlController {

    @Autowired
    private ContentDtlService contentDtlService;

    @Autowired
    private StarCommentService starCommentService;

    @Autowired
    private ContentService contentService;

    @GetMapping("/contentDtl/{contentCode}")
    public String contentDtl(@PathVariable("contentCode") Integer contentCode, Model model ) {
        ContentImgDto repImg = contentDtlService.getRepImg(contentCode);
        ContentDtlDto contentDtl = contentDtlService.getContentDtl(contentCode);
        List<ContentImgDto> contentImgDtoList = contentDtlService.getContentImg(contentCode);
        HolderDto holderDto = contentService.selectHolder(contentCode);
        CreatorDto creatorDto = contentService.selectCreator(contentCode);

        model.addAttribute("repImg", repImg);
        model.addAttribute("contentDtl", contentDtl);
        model.addAttribute("contentImgDtoList", contentImgDtoList);
        model.addAttribute("holder", holderDto);
        model.addAttribute("creator", creatorDto);

        int starAvg = starCommentService.getStarAverage(contentCode);
        model.addAttribute("starAvg", starAvg);

        return "content/contentDtl";
    }



}
