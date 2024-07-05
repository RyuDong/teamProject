package com.green.jhjh.contentManager.form;

import com.green.jhjh.contentManager.dto.ContentImgDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentForm {

    private Integer contentCode;

    @NotBlank(message = "컨텐츠 제목은 필수입니다.")
    private String contentTitle;

    @NotBlank(message = "컨텐츠 타입은 필수입니다.")
    private String contentType;

    @NotNull(message = "행사시작일은 필수입니다.")
    private LocalDate startDate;

    @NotNull(message = "행사종료일은 필수입니다.")
    private LocalDate endDate;

    @NotBlank(message = "장소는 필수입니다.")
    private String place;

    private String runTime;

    private String dayOff;

    private int contentOptionCode;

    @NotBlank(message = "화장실 여부는 필수입니다.")
    private String toilet;

    @NotBlank(message = "주차장 여부는 필수입니다.")
    private String parkingLot;

    @NotBlank(message = "아이동반 여부는 필수입니다.")
    private String forKids;

    @NotBlank(message = "요금 입력은 필수입니다.")
    private String cost;

    @NotBlank(message = "간략 콘텐츠 소개 작성은 필수입니다.")
    private String descriptionShort;

    @NotBlank(message = "전체 콘텐츠 소개 작성은 필수입니다.")
    private String descriptionLong;

    private ContentForm contentOptionForm;

    private List<ContentImgDto> contentImgFileList = new ArrayList<>();

    private List<Integer> contentImgCodes = new ArrayList<>();

    private Integer holderCode;

    private String holderName;

    @NotBlank(message = "개인구분은 필수입니다.")
    private String individual;

    private String enquiry;

    private String enquiryTel;

    private Integer creatorCode;

    @NotBlank(message = "참여자 이름은 필수입니다.")
    private String creatorName;

    private String creatorTel;
}
