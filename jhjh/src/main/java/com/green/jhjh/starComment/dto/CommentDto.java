package com.green.jhjh.starComment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer commentCode;
    private Integer starPointCode;
    private Integer contentCode;
    private String commentText;
    private LocalDateTime commentCreateDate;
    private LocalDateTime commentEditDate;

    private Integer memberCode;
    private String id;
    private String nickname;

    private String contentTitle;
}
