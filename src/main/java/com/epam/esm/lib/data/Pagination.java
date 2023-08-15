package com.epam.esm.lib.data;

import java.util.List;

public class Pagination {
    private int page;
    private int count;
    private List<Sort> sortBy;

    public Pagination(int page, int count, List<Sort> sortBy) {
        this.page = page;
        this.count = count;
        this.sortBy = sortBy;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    public List<Sort> getSortBy() {
        return sortBy;
    }
}
