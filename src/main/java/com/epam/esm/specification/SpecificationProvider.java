package com.epam.esm.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface SpecificationProvider<T> {
    Predicate getSpecification(String[] params, Root<T> root, CriteriaBuilder criteriaBuilder);

    String getFilterKey();
}
