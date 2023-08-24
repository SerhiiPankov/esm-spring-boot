package com.epam.esm.service;

import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Order;
import com.epam.esm.model.ShoppingCart;
import java.math.BigInteger;
import java.util.Map;

public interface OrderService {
    Order completeOrder(ShoppingCart shoppingCart);

    Page<Order> getOrdersHistory(BigInteger userId, Map<String, String> params);
}
