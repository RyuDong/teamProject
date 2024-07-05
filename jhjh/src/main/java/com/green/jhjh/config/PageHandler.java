package com.green.jhjh.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class PageHandler {
    private int totalCnt;
    private int pageSize; //한 페이지에 표시할 데이터 개수

    private int naviSize = 10;

    private int totalPage;
    private int page;
    private int beginPage; //현재 페이지 번호의 naviSize의 첫 페이지
    private int endPage; //현재 페이지 번호의 naviSize의 첫 페이지

    private boolean firstPage; //totalPage 중 첫 naviPage인지
    private boolean lastPage; //totalPage 중 마지막 naviPage인지

    public PageHandler(int totalCnt, int pageSize, int page) {
        this.totalCnt = totalCnt;
        this.pageSize = pageSize;
        this.page = page;

        totalPage = (int) Math.ceil((double)  totalCnt / pageSize);

        beginPage = (page - 1) / naviSize * naviSize + 1;
        endPage = Math.min(beginPage + (naviSize -1), totalPage);

        firstPage = (beginPage == 1);
        lastPage = (endPage == totalPage);
    }

}
