package com.green.jhjh;

import com.green.jhjh.config.PageHandler;
import com.green.jhjh.contentDtl.dto.ContentDtlDto;
import com.green.jhjh.contentDtl.dto.ContentSearchDto;
import com.green.jhjh.contentDtl.service.ContentDtlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HelloController {
    @Autowired
    private ContentDtlService contentDtlService;

    @GetMapping(value={"/", "/{page}"})
    public String main(@PathVariable(value="page", required = false) Integer page, Model model,
                       @ModelAttribute("contentSearchDto") ContentSearchDto contentSearchDto){
        ContentDtlDto monthContent = contentDtlService.getMonthContent();
        model.addAttribute("monthContent", monthContent);

        int ps = 6;
        if(page == null) page = 1;

        Map map = new HashMap();
        map.put("page", page * ps - ps);
        map.put("pageSize", ps);
        map.put("contentSearchDto", contentSearchDto);

        int totalCnt = contentDtlService.countMainContent(map);

        PageHandler pageHandler = new PageHandler(totalCnt, ps, page);
        model.addAttribute("pageHandler", pageHandler);

        List<ContentDtlDto> contentDtlDtoList = contentDtlService.getMainContent(map);
        model.addAttribute("contentsList", contentDtlDtoList);

        return "index";
    }

}
