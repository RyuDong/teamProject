package com.green.jhjh.contentManager.service;

import com.green.jhjh.contentManager.dto.*;
import com.green.jhjh.contentManager.form.ContentForm;
import com.green.jhjh.contentManager.mapper.ContentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentMapper contentMapper;
    private final ContentImgService contentImgService;

    public List<ContentsDto> selectContentsAll(Map map){
        return contentMapper.selectContentsAll(map);
    };

    public int countContents(){
        return contentMapper.countContents();
    }

    public void insertContents(ContentForm contentForm, List<MultipartFile> contentImgFileList)
            throws Exception{
        ContentsDto contentsDto = makeContentsDto(contentForm);
        contentMapper.insertContents(contentsDto);

        ContentOptionDto contentOptionDto = makeContentOptionDto(contentForm);
        contentOptionDto.setContentCode(contentsDto.getContentCode());
        contentMapper.insertContentOption(contentOptionDto);

        for (int i=0; i<contentImgFileList.size(); i++){
            ContentImgDto contentImgDto = new ContentImgDto();
            contentImgDto.setContentCode(contentsDto.getContentCode());

            if(i==0){
                contentImgDto.setRepImgYn("Y");
            }else {
                contentImgDto.setRepImgYn("N");
            }
            contentImgService.saveContentImg(contentImgDto, contentImgFileList.get(i));

        }

        HolderDto holderDto = makeHolderDto(contentForm);
        holderDto.setContentCode(contentsDto.getContentCode());
        contentMapper.insertHolder(holderDto);

        CreatorDto creatorDto = makeCreatorDto(contentForm);
        creatorDto.setContentCode(contentsDto.getContentCode());
        contentMapper.insertCreator(creatorDto);
    }

    public ContentForm getContentDtl(int contentCode){
        ContentsDto contentsDto = contentMapper.selectContents(contentCode);
        ContentOptionDto contentOptionDto = contentMapper.selectContentOption(contentCode);
        HolderDto holderDto = contentMapper.selectHolder(contentCode);
        CreatorDto creatorDto = contentMapper.selectCreator(contentCode);

        if(contentsDto == null){
            throw new NullPointerException("컨텐츠가 존재하지 않습니다.");
        }

        ContentForm contentForm = makeContentForm(contentsDto,contentOptionDto,holderDto,creatorDto);
        contentForm.setContentImgFileList(contentMapper.getContentImgList(contentCode));

        return contentForm;
    }

    public HolderDto selectHolder(int contentCode){
        return contentMapper.selectHolder(contentCode);
    }

    public CreatorDto selectCreator(int contentCode){
        return contentMapper.selectCreator(contentCode);
    }

    public void deleteContent(int contentCode) {
        contentMapper.deleteContent(contentCode);
    }

    public void deleteContentImg(ContentForm contentForm, List<MultipartFile> contentImgFileList)
            throws  Exception{
        List<Integer> contentImgCode = contentForm.getContentImgCodes();
        for (int i = 0; i < contentImgFileList.size(); i++) {
            System.out.println("contentImgCode:" + contentImgCode);
            System.out.println("contentImgFileList" + contentImgFileList);

            contentImgService.deleteContentImg(contentImgCode.get(i), contentImgFileList.get(i));
        }
    }

    public Integer updateContent(ContentForm contentForm,
                                 List<MultipartFile> contentImgFileList) throws Exception{
        ContentsDto contentsDto = makeContentsDto(contentForm);
        contentMapper.updateContent(contentsDto);

        ContentOptionDto contentOptionDto = makeContentOptionDto(contentForm);
        contentOptionDto.setContentCode(contentForm.getContentCode());

        contentMapper.updateContentOption(contentOptionDto);

        List<Integer> contentImgCode = contentForm.getContentImgCodes();
        for (int i=0; i<contentImgFileList.size(); i++){
            contentImgService.updateContentImg(contentImgCode.get(i), contentImgFileList.get(i));
        }

        HolderDto holderDto = makeHolderDto(contentForm);
        holderDto.setContentCode(contentForm.getContentCode());
        contentMapper.updateHolder(holderDto);

        CreatorDto creatorDto = makeCreatorDto(contentForm);
        creatorDto.setContentCode(contentForm.getContentCode());
        contentMapper.updateCreator(creatorDto);

        return contentsDto.getContentCode();
    }

    private ContentsDto makeContentsDto(ContentForm contentForm){
        ContentsDto contentsDto = new ContentsDto();

        contentsDto.setContentCode(contentForm.getContentCode());
        contentsDto.setContentType(contentForm.getContentType());
        contentsDto.setContentTitle(contentForm.getContentTitle());
        contentsDto.setStartDate(contentForm.getStartDate());
        contentsDto.setEndDate(contentForm.getEndDate());
        contentsDto.setRunTime(contentForm.getRunTime());
        contentsDto.setPlace(contentForm.getPlace());
        contentsDto.setDayOff(contentForm.getDayOff());

        return contentsDto;
    }

    private ContentOptionDto makeContentOptionDto(ContentForm contentForm){
        ContentOptionDto contentOptionDto = new ContentOptionDto();

        contentOptionDto.setToilet(contentForm.getToilet());
        contentOptionDto.setParkingLot(contentForm.getParkingLot());
        contentOptionDto.setForKids(contentForm.getForKids());
        contentOptionDto.setCost(contentForm.getCost());
        contentOptionDto.setDescriptionShort(contentForm.getDescriptionShort());
        contentOptionDto.setDescriptionLong(contentForm.getDescriptionLong());

        return contentOptionDto;
    }

    public HolderDto makeHolderDto (ContentForm contentForm){
        HolderDto holderDto = new HolderDto();

        holderDto.setHolderName(contentForm.getHolderName());
        holderDto.setIndividual(contentForm.getIndividual());
        holderDto.setEnquiry(contentForm.getEnquiry());
        holderDto.setEnquiryTel(contentForm.getEnquiryTel());

        return holderDto;
    }

    private CreatorDto makeCreatorDto(ContentForm contentForm){
        CreatorDto creatorDto = new CreatorDto();

        creatorDto.setCreatorName(contentForm.getCreatorName());
        creatorDto.setCreatorTel(contentForm.getCreatorTel());

        return creatorDto;
    }

    private ContentForm makeContentForm(ContentsDto contentsDto,
                                        ContentOptionDto contentOptionDto,
                                        HolderDto holderDto,
                                        CreatorDto creatorDto){
        ContentForm contentForm = new ContentForm();

        contentForm.setContentCode(contentsDto.getContentCode());
        contentForm.setContentType(contentsDto.getContentType());
        contentForm.setContentTitle(contentsDto.getContentTitle());
        contentForm.setStartDate(contentsDto.getStartDate());
        contentForm.setEndDate(contentsDto.getEndDate());
        contentForm.setRunTime(contentsDto.getRunTime());
        contentForm.setPlace(contentsDto.getPlace());
        contentForm.setDayOff(contentsDto.getDayOff());

        contentForm.setContentOptionCode(contentOptionDto.getContentOptionCode());
        contentForm.setToilet(contentOptionDto.getToilet());
        contentForm.setParkingLot(contentOptionDto.getParkingLot());
        contentForm.setForKids(contentOptionDto.getForKids());
        contentForm.setCost(contentOptionDto.getCost());
        contentForm.setDescriptionShort(contentOptionDto.getDescriptionShort());
        contentForm.setDescriptionLong(contentOptionDto.getDescriptionLong());


        contentForm.setHolderCode(holderDto.getHolderCode());
        contentForm.setHolderName(holderDto.getHolderName());
        contentForm.setIndividual(holderDto.getIndividual());
        contentForm.setEnquiry(holderDto.getEnquiry());
        contentForm.setEnquiryTel(holderDto.getEnquiryTel());

        contentForm.setCreatorCode(creatorDto.getCreatorCode());
        contentForm.setCreatorName(creatorDto.getCreatorName());
        contentForm.setCreatorTel(creatorDto.getCreatorTel());
        return contentForm;
    }


}
