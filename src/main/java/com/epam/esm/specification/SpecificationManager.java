package com.epam.esm.specification;

import jakarta.persistence.criteria.Predicate;

public interface SpecificationManager {
    Predicate get(String filterKey, String[] params);
}
