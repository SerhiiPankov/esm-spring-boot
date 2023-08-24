package com.epam.esm.repository.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.model.ShoppingCart;
import com.epam.esm.model.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.ShoppingCartRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ShoppingCartRepositoryImpl extends AbstractRepository<ShoppingCart>
        implements ShoppingCartRepository {
    public ShoppingCartRepositoryImpl(SessionFactory factory) {
        super(factory, ShoppingCart.class, null);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = factory.openSession()) {
            Query<ShoppingCart> getByUser = session.createQuery(
                    "SELECT sc FROM ShoppingCart sc "
                            + " LEFT JOIN FETCH sc.giftCertificates gc "
                            + " WHERE sc.user = :user", ShoppingCart.class);
            getByUser.setParameter("user", user);
            return getByUser.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Not found shopping cart for user " + user);
        }
    }
}
