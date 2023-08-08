package com.epam.esm.specification.impl;

import com.epam.esm.specification.SpecificationProvider;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
public class PartOfNameOrDescriptionSpecificationProvider
        implements SpecificationProvider {
    private static final String FILTER_KEY = "partNameOrDesc";
    private static final String FIELD_NAME = "gc.name";
    private static final String FIELD_DESCRIPTION = "gc.description";

    @Override
    public String getSpecification(String[] partsOfWord) {
        StringJoiner likeName = new StringJoiner(" OR ", FIELD_NAME + " LIKE '%", "%' ");
        StringJoiner likeDescription =
                new StringJoiner(" OR ", FIELD_DESCRIPTION + " LIKE '%", "%' ");
        for (String partOfWord: partsOfWord) {
            likeName.add(partOfWord);
            likeDescription.add(partOfWord);
        }
        return " (" + likeName + " OR " + likeDescription + ") ";
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
