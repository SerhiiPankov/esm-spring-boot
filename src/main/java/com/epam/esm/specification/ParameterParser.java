package com.epam.esm.specification;

import com.epam.esm.lib.data.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
        List<Parameter> filterParams = new ArrayList<>();
        for (Map.Entry<String, String> param: params.entrySet()) {
            if (!ignoreParams.contains(param.getKey())) {
                filterParams.add(
                        new Parameter(param.getKey(), param.getValue().split(",")));
            }
        }
        return filterParams;
    }
}
