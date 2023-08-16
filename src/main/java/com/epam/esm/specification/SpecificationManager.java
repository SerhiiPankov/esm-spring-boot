package com.epam.esm.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface SpecificationManager<T> {
    Predicate get(String filterKey, String[] params, Root<T> root, CriteriaBuilder criteriaBuilder);
}
