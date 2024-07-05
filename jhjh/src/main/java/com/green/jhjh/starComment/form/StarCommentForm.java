package com.green.jhjh.starComment.form;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StarCommentForm {
    private Integer memberCode;

    @Min(value=1, message = "최소 별점은 1점입니다.")
    @Max(value=5, message = "최대 별점은 5점입니다.")
    private Integer starPointValue;

    @NotNull
    private Integer contentCode;

    private String commentText;
}
