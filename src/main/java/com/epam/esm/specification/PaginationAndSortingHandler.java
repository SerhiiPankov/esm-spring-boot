package com.epam.esm.specification;

import com.epam.esm.exception.RequestException;

import java.util.*;

import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaginationAndSortingHandler {
    private final String[] fields;
    private final int defaultCount;
    private final int defaultPage;

    @Autowired
    public PaginationAndSortingHandler() {
        fields = new String[]{"page", "count", "sortBy"};
        defaultCount = 5;
        defaultPage = 1;
    }

    public Pagination handle(Map<String, String> params) {
        int page = params.containsKey(fields[0])
                ? (Integer.parseInt(params.get(fields[0]))) :
                defaultPage;
        int count = params.containsKey(fields[1])
                ? Integer.parseInt(params.get(fields[1])) :
                defaultCount;
        List<Sort> sort = params.containsKey(fields[2]) ? getSort(params) : List.of(new Sort());
        if (page < 0) {
            throw new RequestException("Parameter page must be > 0");
        }
        return new Pagination(page, count, sort);
    }

    private List<Sort> getSort(Map<String, String> params) {
        String sortBy = params.get(fields[2]);
        List<Sort> orders = new ArrayList<>();
        if (sortBy.contains(":")) {
            String[] sortingField = sortBy.split(";");
            for (String field : sortingField) {
                if (field.contains(":")) {
                    String[] fieldAndDirection = field.split(":");
                    orders.add(new Sort(fieldAndDirection[0],
                            Sort.Direction.getDirection(fieldAndDirection[1].toUpperCase())));
                } else {
                    orders.add(new Sort(field, Sort.Direction.ASC));
                }
            }
        }
        return orders;
    }

    public String[] getFields() {
        return fields;
    }
}
