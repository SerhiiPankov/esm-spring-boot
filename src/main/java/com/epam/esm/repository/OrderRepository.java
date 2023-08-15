package com.epam.esm.repository;

import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Specification;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import java.util.List;

public interface OrderRepository {
    Order create(Order order);

    Page<Order> getAll(Specification specification, Pagination pagination);
//
//    List<Order> getOrdersHistory(User user, Pagination pagination);
}
