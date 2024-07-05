package com.green.jhjh.contentDtl.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContentDtlDto {
    private Integer contentCode;
    private String contentTitle;
    private String contentType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String place;
    private String runTime;
    private String dayOff;

    private Integer contentOptionCode;
    private String toilet;
    private String parkingLot;
    private String forKids;
    private String cost;
    private String descriptionShort;
    private String descriptionLong;

    private String imgUrl;
    private String repImgYn;

}
