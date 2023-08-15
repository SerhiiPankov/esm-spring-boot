package com.epam.esm.lib.data;

import java.util.List;

public class Page<T> {
    private List<T> page;
    private Long totalRecords;
    private int numberPage;
    private int numberRecords;

    public Page() {
    }

    public Page(List<T> page, Long totalRecords, int numberPage, int numberRecords) {
        this.page = page;
        this.totalRecords = totalRecords;
        this.numberPage = numberPage;
        this.numberRecords = numberRecords;
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

    public int getNumberRecords() {
        return numberRecords;
    }

    public void setNumberRecords(int numberRecords) {
        this.numberRecords = numberRecords;
    }
}
