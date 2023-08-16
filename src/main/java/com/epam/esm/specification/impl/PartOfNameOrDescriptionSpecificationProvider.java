package com.epam.esm.specification.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.specification.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PartOfNameOrDescriptionSpecificationProvider
        implements SpecificationProvider<GiftCertificate> {
    private static final String FILTER_KEY = "partNameOrDesc";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";

    @Override
    public Predicate getSpecification(String[] partsOfWord,
                                      Root<GiftCertificate> root,
                                      CriteriaBuilder criteriaBuilder) {
        Path<String> namePath = root.get(FIELD_NAME);
        Path<String> descriptionPath = root.get(FIELD_DESCRIPTION);
        List<Predicate> predicates = new ArrayList<>();
        Arrays.stream(partsOfWord).forEach(p -> {
            String[] paramVariants = new String[]{
                    p,
                    String.valueOf(p.charAt(0)).toUpperCase() + p.substring(1).toLowerCase(),
                    p.toUpperCase(),
                    p.toLowerCase()
            };
            for (String paramVariant: paramVariants) {
                predicates.add(criteriaBuilder.like(namePath, "%" + paramVariant + "%"));
                predicates.add(criteriaBuilder.like(descriptionPath, "%" + paramVariant + "%"));
            }
        });
        return criteriaBuilder.or(predicates.toArray(predicates.toArray(new Predicate[0])));
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
