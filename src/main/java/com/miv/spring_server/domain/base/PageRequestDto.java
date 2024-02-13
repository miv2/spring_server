package com.miv.spring_server.domain.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestDto {
    private int page;
    private int size;
    private String keyword;
    private String startDate;
    private String endDate;

    public PageRequestDto(int page, int size, String type, String keyword, String startDate, String endDate) {
        this.page = page == 0 ? 1 : page;
        this.size = size == 0 ? 10 : size;
        this.keyword = keyword;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PageRequestDto(){
        this.page = 1;
        this.size = 10;
    }

    public void setPage(int page) {
        this.page = page == 0 ? 1 : page;;
    }

    public void setSize(int size) {
        this.size = size == 0 ? 10 : size;;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Pageable getPageable(){
        return PageRequest.of(page - 1, size);
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page - 1, size, sort);
    }
}
