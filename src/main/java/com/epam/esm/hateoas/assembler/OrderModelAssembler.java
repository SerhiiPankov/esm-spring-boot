package com.epam.esm.hateoas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.hateoas.model.OrderModel;
import java.util.HashMap;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler extends
        RepresentationModelAssemblerSupport<OrderResponseDto, OrderModel> {
    public OrderModelAssembler() {
        super(UserController.class, OrderModel.class);
    }

    @Override
    public OrderModel toModel(OrderResponseDto orderResponseDto) {
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderResponseDto, orderModel);
        orderModel.add(linkTo(methodOn(UserController.class)
                .getOrdersHistoryByUserId(orderResponseDto.getUser().getId(), new HashMap<>()))
                .withRel("orders-history"));
        orderModel.add(linkTo(methodOn(UserController.class)
                .getShoppingCartByUserId(orderResponseDto.getUser().getId())).withRel("user-cart"));
        orderModel.add(linkTo(methodOn(UserController.class)
                .getUserById(orderResponseDto.getUser().getId())).withRel("get-user"));
        orderModel.add(linkTo(methodOn(UserController.class)
                .getAllUsers(new HashMap<>())).withRel("all-users"));
        return orderModel;
    }
}
