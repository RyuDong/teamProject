package com.green.jhjh.contentDtl.service;

import com.green.jhjh.contentDtl.dto.ContentDtlDto;
import com.green.jhjh.contentDtl.mapper.ContentDtlMapper;
import com.green.jhjh.contentManager.dto.ContentImgDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContentDtlService {

    @Autowired
    private ContentDtlMapper contentDtlMapper;

    public ContentImgDto getRepImg(int contentCode) {
        return contentDtlMapper.getRepImg(contentCode);
    }

    public List<ContentImgDto> getContentImg(int contentCode) {
        return contentDtlMapper.getContentImg(contentCode);
    }

    public ContentDtlDto getContentDtl(int contentCode){
        return contentDtlMapper.getContentDtl(contentCode);
    }

    public ContentDtlDto getMonthContent(){
        return contentDtlMapper.getMonthContent();
    }

    public List<ContentDtlDto> getMainContent(Map map){
        return contentDtlMapper.getMainContent(map);
    }

    public int countMainContent(Map map){
        return contentDtlMapper.countMainContent(map);
    }

}
