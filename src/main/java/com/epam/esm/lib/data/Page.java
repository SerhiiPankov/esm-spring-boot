package com.epam.esm.lib.data;

import java.util.List;

public class Page<T> {
    private List<T> page;
    private Long totalRecords;
    private int numberPage;
    private int numberRecordsPerPage;

    public Page() {
    }

    public Page(List<T> page, Long totalRecords, int numberPage, int numberRecordsPerPage) {
        this.page = page;
        this.totalRecords = totalRecords;
        this.numberPage = numberPage;
        this.numberRecordsPerPage = numberRecordsPerPage;
    }

    public List<T> getPage() {
        return page;
    }

    public void setPage(List<T> page) {
        this.page = page;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public int getNumberRecordsPerPage() {
        return numberRecordsPerPage;
    }

    public void setNumberRecordsPerPage(int numberRecordsPerPage) {
        this.numberRecordsPerPage = numberRecordsPerPage;
    }
}
