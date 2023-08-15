package com.epam.esm.controller;

import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.lib.data.Page;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.ShoppingCartService;
import com.epam.esm.service.UserService;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;

    public OrderController(OrderMapper orderMapper, UserService userService,
                           ShoppingCartService shoppingCartService, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    @PostMapping("/complete")
    public OrderResponseDto completeOrder(@RequestParam BigInteger userId) {
        User user = userService.get(userId);
        return orderMapper.mapToDto(orderService.completeOrder(
                shoppingCartService.getByUser(user)));
    }

    @GetMapping("/user")
    public Page<OrderResponseDto> getUserOrdersHistory(@RequestParam BigInteger userId,
                                                       @RequestParam Map<String, String> params) {
        User user = userService.get(userId);
        return orderMapper.mapPageDto(orderService.getOrdersHistory(user, params));
    }
}
