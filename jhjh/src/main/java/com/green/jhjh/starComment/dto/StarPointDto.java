package com.green.jhjh.starComment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StarPointDto {
    private Integer starPointCode;
    private Integer starPointValue;
    private LocalDateTime starPointDate;
    private Integer contentCode;
    private Integer MemberCode;

}
