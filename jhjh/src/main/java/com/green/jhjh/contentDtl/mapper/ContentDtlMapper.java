package com.green.jhjh.contentDtl.mapper;

import com.green.jhjh.contentDtl.dto.ContentDtlDto;
import com.green.jhjh.contentManager.dto.ContentImgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContentDtlMapper {
    ContentImgDto getRepImg(int contentCode);

    List<ContentImgDto> getContentImg(int contentCode);

    ContentDtlDto getContentDtl(int contentCode);

    ContentDtlDto getMonthContent();

    List<ContentDtlDto> getMainContent(Map map);

    int countMainContent(Map map);
}
