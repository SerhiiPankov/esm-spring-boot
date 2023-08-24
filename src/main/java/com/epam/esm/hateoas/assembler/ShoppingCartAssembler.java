package com.epam.esm.hateoas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.ShoppingCartResponseDto;
import com.epam.esm.hateoas.model.ShoppingCartModel;
import java.util.HashMap;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartAssembler extends
        RepresentationModelAssemblerSupport<ShoppingCartResponseDto, ShoppingCartModel> {
    public ShoppingCartAssembler() {
        super(UserController.class, ShoppingCartModel.class);
    }

    @Override
    public ShoppingCartModel toModel(ShoppingCartResponseDto shoppingCartResponseDto) {
        ShoppingCartModel shoppingCartModel = new ShoppingCartModel();
        BeanUtils.copyProperties(shoppingCartResponseDto, shoppingCartModel);

        shoppingCartModel.add(linkTo(methodOn(UserController.class)
                .getShoppingCartByUserId(shoppingCartResponseDto.getUserId())).withSelfRel());
        shoppingCartModel.add(linkTo(methodOn(UserController.class)
                .completeOrder(shoppingCartResponseDto.getUserId())).withRel("complete-order"));
        shoppingCartModel.add(linkTo(methodOn(UserController.class)
                .getUserById(shoppingCartResponseDto.getUserId())).withRel("get-user"));
        shoppingCartModel.add(linkTo(methodOn(UserController.class)
                .getOrdersHistoryByUserId(shoppingCartResponseDto.getUserId(),
                        new HashMap<>())).withRel("orders-history"));
        shoppingCartModel.add(linkTo(methodOn(UserController.class)
                .getAllUsers(new HashMap<>())).withRel("all-users"));
        return shoppingCartModel;
    }
}
