package com.epam.esm.specification;

public interface SpecificationProvider {
    String getSpecification(String[] params);

    String getFilterKey();
}
