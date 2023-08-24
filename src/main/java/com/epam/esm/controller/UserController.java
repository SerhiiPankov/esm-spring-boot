package com.epam.esm.controller;

import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.dto.UserRequestDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.hateoas.PageMetadataParser;
import com.epam.esm.hateoas.assembler.MessageModelAssembler;
import com.epam.esm.hateoas.assembler.OrderModelAssembler;
import com.epam.esm.hateoas.assembler.ShoppingCartAssembler;
import com.epam.esm.hateoas.assembler.UserModelAssembler;
import com.epam.esm.hateoas.model.MessageModel;
import com.epam.esm.hateoas.model.OrderModel;
import com.epam.esm.hateoas.model.ShoppingCartModel;
import com.epam.esm.hateoas.model.UserModel;
import com.epam.esm.lib.data.Page;
import com.epam.esm.mapper.MessageMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.ShoppingCartMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.ShoppingCartService;
import com.epam.esm.service.UserService;
import jakarta.validation.Valid;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final OrderService orderService;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final MessageMapper messageMapper;
    private final OrderMapper orderMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserMapper userMapper;
    private final MessageModelAssembler messageModelAssembler;
    private final OrderModelAssembler orderModelAssembler;
    private final UserModelAssembler userModelAssembler;
    private final ShoppingCartAssembler shoppingCartAssembler;
    private final PageMetadataParser pageMetadataParser;

    public UserController(OrderService orderService,
                          RoleService roleService,
                          ShoppingCartService shoppingCartService,
                          UserService userService,
                          GiftCertificateService giftCertificateService,
                          MessageMapper messageMapper,
                          OrderMapper orderMapper,
                          ShoppingCartMapper shoppingCartMapper,
                          UserMapper userMapper,
                          OrderModelAssembler orderModelAssembler,
                          UserModelAssembler userModelAssembler,
                          MessageModelAssembler messageModelAssembler,
                          ShoppingCartAssembler shoppingCartAssembler,
                          PageMetadataParser pageMetadataParser) {
        this.orderService = orderService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.messageMapper = messageMapper;
        this.orderMapper = orderMapper;
        this.shoppingCartMapper = shoppingCartMapper;
        this.userMapper = userMapper;
        this.orderModelAssembler = orderModelAssembler;
        this.userModelAssembler = userModelAssembler;
        this.messageModelAssembler = messageModelAssembler;
        this.shoppingCartAssembler = shoppingCartAssembler;
        this.pageMetadataParser = pageMetadataParser;
    }

    @PostMapping
    public UserModel registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userModelAssembler.toModel(userMapper.mapToDto(userService.register(
                userRequestDto.getEmail(), userRequestDto.getPassword(),
                Set.of(roleService.getByName(Role.RoleName.USER.name())))));
    }

    @GetMapping("/{userId}")
    public UserModel getUserById(@PathVariable BigInteger userId) {
        return userModelAssembler.toModel(userMapper.mapToDto(userService.get(userId)));
    }

    @GetMapping
    public PagedModel<UserModel> getAllUsers(@RequestParam Map<String, String> params) {
        Page<UserResponseDto> userResponseDtoPage =
                userMapper.mapPageDto(userService.getAll(params));
        return PagedModel.of(userResponseDtoPage.getPage().stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(userResponseDtoPage));
    }

    @PutMapping("/{userId}/cart")
    public MessageModel addGiftCertificateToCart(@PathVariable BigInteger userId,
                                                 @RequestParam BigInteger giftCertificateId) {
        User user = userService.get(userId);
        GiftCertificate giftCertificate = giftCertificateService.get(giftCertificateId);
        shoppingCartService.addGiftCertificate(giftCertificate, user);
        return messageModelAssembler.toModel(
                messageMapper.mapToTagResponseDto("Gift certificate was added to shopping cart"));
    }

    @GetMapping("/{userId}/cart")
    public ShoppingCartModel getShoppingCartByUserId(@PathVariable BigInteger userId) {
        User user = userService.get(userId);
        return shoppingCartAssembler.toModel(
                shoppingCartMapper.mapToDto(shoppingCartService.getByUser(user)));
    }

    @PostMapping("/{userId}/order")
    public OrderModel completeOrder(@PathVariable BigInteger userId) {
        User user = userService.get(userId);
        return orderModelAssembler.toModel(orderMapper.mapToDto(orderService.completeOrder(
                shoppingCartService.getByUser(user))));
    }

    @GetMapping("/{userId}/order")
    public PagedModel<OrderModel> getOrdersHistoryByUserId(@PathVariable BigInteger userId,
                                                   @RequestParam Map<String, String> params) {
        Page<OrderResponseDto> orderResponseDtoPage =
                orderMapper.mapPageDto(orderService.getOrdersHistory(userId, params));
        return PagedModel.of(orderResponseDtoPage.getPage().stream()
                .map(orderModelAssembler::toModel)
                .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(orderResponseDtoPage));
    }
}
