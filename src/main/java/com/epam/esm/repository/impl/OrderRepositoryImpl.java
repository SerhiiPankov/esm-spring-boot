package com.epam.esm.repository.impl;

import com.epam.esm.model.Order;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.specification.SpecificationManager;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {
    public OrderRepositoryImpl(SessionFactory factory,
                               SpecificationManager<Order> specificationManager) {
        super(factory, Order.class, specificationManager);
    }
}
