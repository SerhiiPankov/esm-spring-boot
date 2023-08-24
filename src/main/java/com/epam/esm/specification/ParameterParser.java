package com.epam.esm.specification;

import com.epam.esm.lib.data.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ParameterParser {
    private final PaginationAndSortingHandler paginationAndSortingHandler;

    public ParameterParser(PaginationAndSortingHandler paginationAndSortingHandler) {
        this.paginationAndSortingHandler = paginationAndSortingHandler;
    }

    public List<Parameter> parseParameters(Map<String, String> params) {
        List<String> ignoreParams = new ArrayList<>();
        Collections.addAll(ignoreParams, paginationAndSortingHandler.getFields());
        return params.entrySet().stream()
                .filter(p -> !ignoreParams.contains(p.getKey()))
                .map(p -> new Parameter(p.getKey(), p.getValue().split(",")))
                .collect(Collectors.toList());
    }
}
