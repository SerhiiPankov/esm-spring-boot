package com.epam.esm.specification.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.specification.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class TagNameSpecificationProvider
        implements SpecificationProvider<GiftCertificate> {
    private static final String FILTER_KEY = "tagName";
    private static final String FIELD_NAME = "tags";

    @Override
    public Predicate getSpecification(String[] params,
                                      Root<GiftCertificate> root,
                                      CriteriaBuilder criteriaBuilder) {
        Join<GiftCertificate, Tag> tagsJoin = root.join(FIELD_NAME, JoinType.LEFT);
        CriteriaBuilder.In<String> predicateIn = criteriaBuilder.in(tagsJoin.get("name"));
        for (String value: params) {
            predicateIn.value(value);
        }
        return predicateIn;
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
