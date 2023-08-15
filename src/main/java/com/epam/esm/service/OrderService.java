package com.epam.esm.service;

import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Order;
import com.epam.esm.model.ShoppingCart;
import com.epam.esm.model.User;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order completeOrder(ShoppingCart shoppingCart);

    Page<Order> getOrdersHistory(User user, Map<String, String> params);
}
