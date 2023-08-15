package com.epam.esm.specification.impl;

import com.epam.esm.exception.RequestException;
import com.epam.esm.specification.SpecificationManager;
import com.epam.esm.specification.SpecificationProvider;
import jakarta.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SpecificationManagerImpl implements SpecificationManager {
    private final Map<String, SpecificationProvider> providerMap;

    public SpecificationManagerImpl(
            List<SpecificationProvider> specifications) {
        this.providerMap = specifications.stream()
                .collect(Collectors.toMap(SpecificationProvider::getFilterKey,
                        Function.identity()));
    }

    @Override
    public Predicate get(String filterKey, String[] params) {
        if (!providerMap.containsKey(filterKey)) {
            throw new RequestException("Key " + filterKey + " is not supported for data filtering");
        }
        return providerMap.get(filterKey).getSpecification(params);
    }
}
