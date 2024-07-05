package com.green.jhjh.contentManager.controller;


import com.green.jhjh.config.PageHandler;
import com.green.jhjh.contentManager.dto.ContentImgDto;
import com.green.jhjh.contentManager.dto.ContentsDto;
import com.green.jhjh.contentManager.form.ContentForm;
import com.green.jhjh.contentManager.service.ContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ContentController {

    private final ContentService contentService;

    @GetMapping(value={"/contentList", "/contentList/{page}"})
    public String contentList(@PathVariable(value="page", required = false) Integer page, Model model){
        int ps = 10;
        if(page == null) page = 1;

        Map map = new HashMap();
        map.put("page", page * ps - ps);
        map.put("pageSize", ps);

        int totalCnt = contentService.countContents();

        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);
        model.addAttribute("pageHandler", pageHandler);

        List<ContentsDto> contents = contentService.selectContentsAll(map);
        model.addAttribute("contents", contents);

        return "contentManager/contentList";
    }

    @GetMapping("/addContent")
    public String contentAdd(Model model){
        model.addAttribute("contentForm", new ContentForm());
        return "contentManager/addContent";
    }

    @PostMapping("/content/new")
    public String contentNew(@Valid ContentForm contentForm,
                             BindingResult bindingResult,
                             Model model,
                             @RequestParam("contentImgFile") List<MultipartFile> contentImgFileList,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "contentManager/addContent";
        }

        // 이미지 파일이 비어 있는지 또는 첫 번째 이미지가 비어 있는지 확인
        if (contentImgFileList == null || contentImgFileList.isEmpty() || contentImgFileList.get(0).isEmpty()) {
            model.addAttribute("errorMessage", "첫 번째 이미지는 필수입니다.");
            return "contentManager/addContent";
        }

        try {
            contentService.insertContents(contentForm, contentImgFileList);
            redirectAttributes.addFlashAttribute("successMessage", "콘텐츠가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "콘텐츠 등록 중 에러 발생");
        }

        return "redirect:/admin/contentList";
    }

    @PostMapping("/delete/{contentCode}")
    public String deleteContent(Model model, @RequestParam("contentImgFile") List<MultipartFile> contentImgFileList,
                                ContentForm contentForm, RedirectAttributes redirectAttributes) {
        try {
            contentService.deleteContentImg(contentForm, contentImgFileList);
        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            return "/contentManager/addContent";
        }

        try {
            contentService.deleteContent(contentForm.getContentCode());
            redirectAttributes.addFlashAttribute("successMessage", "콘텐츠가 성공적으로 삭제되었습니다.");
        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "콘텐츠 삭제 중 에러 발생");
            return "/contentManager/addContent";
        }

        return "redirect:/admin/contentList";
    }

    @GetMapping("/update/{contentCode}")
    public String ContentDtl(@PathVariable("contentCode") int contentCode,
                             Model model){
        try {
            ContentForm contentForm = contentService.getContentDtl(contentCode);
            model.addAttribute("contentForm", contentForm);
            model.addAttribute("contentImgDtoList", contentForm.getContentImgFileList());
        }catch (NullPointerException e){
            model.addAttribute("errorMessage", "존재하지않는 컨텐츠입니다.");
            model.addAttribute("contentForm", new ContentForm());
            return "contentManager/addContent";
        }
        return "contentManager/addContent";
    }

    @PostMapping("/update/{contentCode}")
    public String updateContent(@Valid ContentForm contentForm,
                                BindingResult bindingResult,
                                Model model,
                                @RequestParam("contentImgFile") List<MultipartFile> contentImgFileList,
                                RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "contentManager/addContent";
        }

        if(contentImgFileList.get(0).isEmpty() && contentForm.getContentCode() == null) {
            model.addAttribute("errorMessage", "첫번째 이미지는 필수입니다.");
            return "contentManager/addContent";
        }
        
        try {
            contentService.updateContent(contentForm, contentImgFileList);
            redirectAttributes.addFlashAttribute("successMessage", "성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "컨텐츠 수정 중 에러 발생");
            return "contentManager/contentList";
        }
        return "redirect:/admin/contentList";
    }

}
