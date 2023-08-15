package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Specification;
import com.epam.esm.model.Order;
import com.epam.esm.model.ShoppingCart;
import com.epam.esm.model.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.epam.esm.specification.PaginationAndSortingHandler;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final PaginationAndSortingHandler paginationAndSortingHandler;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ShoppingCartService shoppingCartService, PaginationAndSortingHandler paginationAndSortingHandler) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.paginationAndSortingHandler = paginationAndSortingHandler;
    }

    @Override
    public Order completeOrder(ShoppingCart shoppingCart) {
        if (shoppingCart.getGiftCertificates().size() == 0) {
            throw new DataProcessingException(
                    "Cannot complete order because shopping cart is empty");
        }
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setGiftCertificates(shoppingCart.getGiftCertificates());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setPurchaseDate(LocalDateTime.now());
        orderRepository.create(order);
        shoppingCartService.clear(shoppingCart);
        return order;
    }

    @Override
    public Page<Order> getOrdersHistory(User user, Map<String, String> params) {




        return orderRepository.getAll(new Specification(), paginationAndSortingHandler.handle(params));
    }
}
