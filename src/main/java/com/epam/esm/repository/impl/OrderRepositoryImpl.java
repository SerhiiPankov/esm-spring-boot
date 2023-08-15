package com.epam.esm.repository.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.OrderRepository;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {
    public OrderRepositoryImpl(SessionFactory factory) {
        super(factory, Order.class);
    }

//    @Override
//    public List<Order> getOrdersHistory(User user, Pagination pagination) {
//        try (Session session = factory.openSession()) {
//            Query<Order> getByUser = session.createQuery(
//                    "SELECT DISTINCT o FROM Order o "
//                            + "join fetch o.giftCertificates g "
//                            + "WHERE o.user = :user", Order.class);
//            getByUser.setParameter("user", user);
//            return getByUser.getResultList();
//        } catch (Exception e) {
//            throw new DataProcessingException("Not found shopping cart for user " + user);
//        }
//    }
}
