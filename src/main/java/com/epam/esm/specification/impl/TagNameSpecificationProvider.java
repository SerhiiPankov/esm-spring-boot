package com.epam.esm.specification.impl;

import com.epam.esm.specification.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class TagNameSpecificationProvider
        implements SpecificationProvider {
    private static final String FILTER_KEY = "tagName";
    private static final String FIELD_NAME = "t.name";

    @Override
    public Predicate getSpecification(String[] tagNames) {
        return null;
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
