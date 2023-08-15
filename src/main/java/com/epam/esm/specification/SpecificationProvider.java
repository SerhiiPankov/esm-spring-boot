package com.epam.esm.specification;

import jakarta.persistence.criteria.Predicate;

public interface SpecificationProvider {
    Predicate getSpecification(String[] params);

    String getFilterKey();
}
