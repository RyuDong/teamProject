package com.green.jhjh.contentManager.mapper;

import com.green.jhjh.contentManager.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContentMapper {
    List<ContentsDto> selectContentsAll(Map map);

    int countContents();

    int insertContents(ContentsDto contentsDto);

    int insertContentImg(ContentImgDto contentImgDto);

    int insertContentOption(ContentOptionDto contentOptionDto);

    int insertHolder(HolderDto holderDto);

    int insertCreator(CreatorDto creatorDto);

    ContentsDto selectContents(int contentCode);

    ContentOptionDto selectContentOption(int contentCode);

    List<ContentImgDto> getContentImgList (int contentCode);

    ContentImgDto selectContentImg(int ContentImgCode);

    HolderDto selectHolder(int contentCode);

    CreatorDto selectCreator(int contentCode);

    int deleteContent(int contentCode);

    int updateContent(ContentsDto contentsDto);

    int updateContentOption(ContentOptionDto contentOptionDto);

    int updateContentImg(ContentImgDto contentImgDto);

    int updateHolder(HolderDto holderDto);

    int updateCreator(CreatorDto creatorDto);
}
