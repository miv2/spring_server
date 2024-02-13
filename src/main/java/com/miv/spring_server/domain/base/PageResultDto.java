package com.miv.spring_server.domain.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageResultDto<DTO, EN> {

    //DTO리스트
    private List<DTO> dtoList;

    //총 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;

    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    private long totalCount;

    //페이지 번호  목록
    private List<Integer> pageList;

    public List<DTO> getDtoList() {
        return dtoList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public List<Integer> getPageList() {
        return pageList;
    }

    public PageResultDto(Page<EN> result, Function<EN,DTO> fn ){

        dtoList = result.stream()
                .map(fn)
                .collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable(), result.getTotalElements());
    }

    private void makePageList(Pageable pageable, long totalCount){

        this.page = pageable.getPageNumber() +1; // 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;

        prev = page > 1;

        next = totalPage > page;

        end = totalPage > tempEnd ? tempEnd: totalPage;

        this.totalCount = totalCount;

        pageList = IntStream.rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toList());
    }
}
