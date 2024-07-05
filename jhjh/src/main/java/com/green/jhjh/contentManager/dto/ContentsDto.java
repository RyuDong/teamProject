package com.green.jhjh.contentManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentsDto {
    private Integer contentCode;
    private String contentTitle;
    private String contentType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String runTime;
    private String dayOff;
    private String place;

}
