package com.epam.esm.hateoas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.hateoas.model.UserModel;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler extends
        RepresentationModelAssemblerSupport<UserResponseDto, UserModel> {
    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public @NotNull UserModel toModel(@NotNull UserResponseDto userResponseDto) {

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userResponseDto, userModel);
        userModel.add(linkTo(methodOn(UserController.class)
                .getUserById(userResponseDto.getId())).withSelfRel());
        userModel.add(linkTo(methodOn(UserController.class)
                .getAllUsers(new HashMap<>())).withRel("all-users"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userModel.add(linkTo(methodOn(UserController.class)
                .getShoppingCart(authentication)).withRel("user-cart"));
        userModel.add(linkTo(methodOn(UserController.class)
                .completeOrder(authentication)).withRel("complete-order"));
        userModel.add(linkTo(methodOn(UserController.class)
                .getOrdersHistory(authentication,
                        new HashMap<>())).withRel("orders-history"));
        return userModel;
    }
}
