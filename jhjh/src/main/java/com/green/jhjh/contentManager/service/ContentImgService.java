package com.green.jhjh.contentManager.service;

import com.green.jhjh.contentManager.dto.ContentImgDto;
import com.green.jhjh.contentManager.mapper.ContentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ContentImgService {

    private final FileService fileService;
    private final ContentMapper contentMapper;

    @Value("${contentImgLocation}")
    private String contentImgLocation;

    public void saveContentImg(ContentImgDto contentImgDto, MultipartFile contentImgFile) throws Exception {

        String oriImgName = contentImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(contentImgLocation, oriImgName, contentImgFile.getBytes());
            imgUrl = "/images/content/" + imgName;
        }

        contentImgDto.setImgName(imgName);
        contentImgDto.setOriImgName(oriImgName);
        contentImgDto.setImgUrl(imgUrl);

        contentMapper.insertContentImg(contentImgDto);

    }

    public void updateContentImg(int contentImgCode, MultipartFile contentImgFile) throws Exception {

        if (!contentImgFile.isEmpty()) {
            ContentImgDto savedContentImg = contentMapper.selectContentImg(contentImgCode);

            if (!StringUtils.isEmpty(savedContentImg.getImgName())) {
                fileService.deleteFile(contentImgLocation + "/" + savedContentImg.getImgName());
            }

            String oriImgName = contentImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(contentImgLocation, oriImgName, contentImgFile.getBytes());
            String imgUrl = "/images/content/" + imgName;

            savedContentImg.setOriImgName(oriImgName);
            savedContentImg.setImgName(imgName);
            savedContentImg.setImgUrl(imgUrl);

            contentMapper.updateContentImg(savedContentImg);
        }
    }

    public void deleteContentImg(int contentImgCode, MultipartFile contentImgFile) throws Exception {
        if (!contentImgFile.isEmpty()) {
            ContentImgDto savedContentImg = contentMapper.selectContentImg(contentImgCode);

            if (!StringUtils.isEmpty(savedContentImg.getImgName())) {
                fileService.deleteFile(contentImgLocation + "/" + savedContentImg.getImgName());
            }
        }
    }

}