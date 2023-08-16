package com.epam.esm.specification.impl;

import com.epam.esm.exception.RequestException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.specification.SpecificationManager;
import com.epam.esm.specification.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateSpecificationManager implements SpecificationManager<GiftCertificate> {
    private final Map<String, SpecificationProvider<GiftCertificate>> providerMap;

    public GiftCertificateSpecificationManager(
            List<SpecificationProvider<GiftCertificate>> giftCertificateSpecification) {
        this.providerMap = giftCertificateSpecification.stream()
                .collect(Collectors.toMap(SpecificationProvider::getFilterKey,
                        Function.identity()));
    }

    @Override
    public Predicate get(String filterKey, String[] params,
                         Root<GiftCertificate> root,
                         CriteriaBuilder criteriaBuilder) {
        if (!providerMap.containsKey(filterKey)) {
            throw new RequestException("Key " + filterKey + " is not supported for data filtering");
        }
        return providerMap.get(filterKey).getSpecification(params, root, criteriaBuilder);
    }
}
