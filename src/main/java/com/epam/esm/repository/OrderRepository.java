package com.epam.esm.repository;

import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Parameter;
import com.epam.esm.model.Order;
import java.util.List;

public interface OrderRepository {
    Order create(Order order);

    Page<Order> getAll(List<Parameter> filterParams, Pagination pagination);
}
