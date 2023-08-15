package com.epam.esm.specification.impl;

import com.epam.esm.specification.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class PartOfNameOrDescriptionSpecificationProvider
        implements SpecificationProvider {
    private static final String FILTER_KEY = "partNameOrDesc";
    private static final String FIELD_NAME = "gc.name";
    private static final String FIELD_DESCRIPTION = "gc.description";

    @Override
    public Predicate getSpecification(String[] partsOfWord) {

        return null;
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
