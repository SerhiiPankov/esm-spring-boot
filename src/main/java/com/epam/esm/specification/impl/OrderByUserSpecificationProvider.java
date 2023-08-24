package com.epam.esm.specification.impl;

import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.specification.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigInteger;
import org.springframework.stereotype.Component;

@Component
public class OrderByUserSpecificationProvider implements SpecificationProvider<Order> {
    private static final String FILTER_KEY = "userId";
    private static final String FIELD_NAME_USER = "user";

    @Override
    public Predicate getSpecification(String[] params, Root<Order> root,
                                      CriteriaBuilder criteriaBuilder) {
        Join<Order, User> userJoin = root.join(FIELD_NAME_USER, JoinType.LEFT);
        CriteriaBuilder.In<BigInteger> predicateIn = criteriaBuilder.in(userJoin.get("id"));
        for (String value: params) {
            predicateIn.value(BigInteger.valueOf(Long.parseLong(value)));
        }
        return predicateIn;
    }

    @Override
    public String getFilterKey() {
        return FILTER_KEY;
    }
}
