package com.epam.esm.controller;

import com.epam.esm.dto.JwtResponseDto;
import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.dto.TagResponseDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.dto.UserSignInRequestDto;
import com.epam.esm.dto.UserSignUpRequestDto;
import com.epam.esm.hateoas.PageMetadataParser;
import com.epam.esm.hateoas.assembler.MessageModelAssembler;
import com.epam.esm.hateoas.assembler.OrderModelAssembler;
import com.epam.esm.hateoas.assembler.ShoppingCartAssembler;
import com.epam.esm.hateoas.assembler.TagModelAssembler;
import com.epam.esm.hateoas.assembler.UserModelAssembler;
import com.epam.esm.hateoas.model.MessageModel;
import com.epam.esm.hateoas.model.OrderModel;
import com.epam.esm.hateoas.model.ShoppingCartModel;
import com.epam.esm.hateoas.model.TagModel;
import com.epam.esm.hateoas.model.UserModel;
import com.epam.esm.lib.data.Page;
import com.epam.esm.mapper.MessageMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.ShoppingCartMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.JwtService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.ShoppingCartService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import jakarta.validation.Valid;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final TagService tagService;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final MessageMapper messageMapper;
    private final OrderMapper orderMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final TagMapper tagMapper;
    private final UserMapper userMapper;
    private final MessageModelAssembler messageModelAssembler;
    private final OrderModelAssembler orderModelAssembler;
    private final UserModelAssembler userModelAssembler;
    private final ShoppingCartAssembler shoppingCartAssembler;
    private final TagModelAssembler tagModelAssembler;
    private final PageMetadataParser pageMetadataParser;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(OrderService orderService,
                          RoleService roleService,
                          ShoppingCartService shoppingCartService,
                          TagService tagService,
                          UserService userService,
                          GiftCertificateService giftCertificateService,
                          MessageMapper messageMapper,
                          OrderMapper orderMapper,
                          ShoppingCartMapper shoppingCartMapper,
                          TagMapper tagMapper,
                          UserMapper userMapper,
                          OrderModelAssembler orderModelAssembler,
                          UserModelAssembler userModelAssembler,
                          MessageModelAssembler messageModelAssembler,
                          ShoppingCartAssembler shoppingCartAssembler,
                          TagModelAssembler tagModelAssembler,
                          PageMetadataParser pageMetadataParser,
                          UserDetailsService userDetailsService,
                          JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.orderService = orderService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
        this.tagService = tagService;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.messageMapper = messageMapper;
        this.orderMapper = orderMapper;
        this.shoppingCartMapper = shoppingCartMapper;
        this.tagMapper = tagMapper;
        this.userMapper = userMapper;
        this.orderModelAssembler = orderModelAssembler;
        this.userModelAssembler = userModelAssembler;
        this.messageModelAssembler = messageModelAssembler;
        this.shoppingCartAssembler = shoppingCartAssembler;
        this.tagModelAssembler = tagModelAssembler;
        this.pageMetadataParser = pageMetadataParser;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth/register")
    public UserModel registerUser(@RequestBody @Valid UserSignUpRequestDto userSignupRequestDto) {
        return userModelAssembler.toModel(
                userMapper.mapToDto(
                        userService.register(
                userSignupRequestDto.getEmail(), userSignupRequestDto.getPassword(),
                Set.of(roleService.getByName(Role.RoleName.USER.name())))));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(
            @RequestBody @Valid UserSignInRequestDto userSigninRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userSigninRequestDto.getEmail(), userSigninRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(messageModelAssembler.toModel(
                    messageMapper.mapToTagResponseDto("Can not login user")),
                    HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(userSigninRequestDto.getEmail());
        String token = jwtService.generateToken(userDetails);
        return new ResponseEntity<>(new JwtResponseDto(token), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/info")
    public UserModel getUserById(@PathVariable BigInteger userId) {
        return userModelAssembler.toModel(userMapper.mapToDto(userService.get(userId)));
    }

    @GetMapping("/info")
    public UserModel getUser(Authentication authentication) {
        return userModelAssembler.toModel(
                userMapper.mapToDto(
                        userService.getUserByEmail(authentication.getName())));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public PagedModel<UserModel> getAllUsers(@RequestParam Map<String, String> params) {
        Page<UserResponseDto> userResponseDtoPage =
                userMapper.mapPageDto(userService.getAll(params));
        return PagedModel.of(userResponseDtoPage.getPage().stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(userResponseDtoPage));
    }

    @PutMapping("/cart")
    public MessageModel addGiftCertificateToCart(Authentication authentication,
                                                 @RequestParam BigInteger giftCertificateId) {
        User user = userService.getUserByEmail(authentication.getName());
        GiftCertificate giftCertificate = giftCertificateService.get(giftCertificateId);
        shoppingCartService.addGiftCertificate(giftCertificate, user);
        return messageModelAssembler.toModel(
                messageMapper.mapToTagResponseDto("Gift certificate was added to shopping cart"));
    }

    @GetMapping("/cart")
    public ShoppingCartModel getShoppingCart(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        return shoppingCartAssembler.toModel(
                shoppingCartMapper.mapToDto(shoppingCartService.getByUser(user)));
    }

    @PostMapping("/orders")
    public OrderModel completeOrder(Authentication authentication) {
        User user = userService.getUserByEmail(authentication.getName());
        return orderModelAssembler.toModel(orderMapper.mapToDto(orderService.completeOrder(
                shoppingCartService.getByUser(user))));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/orders")
    public PagedModel<OrderModel> getOrdersHistoryByUserId(@PathVariable BigInteger userId,
                                                   @RequestParam Map<String, String> params) {
        Page<OrderResponseDto> orderResponseDtoPage =
                orderMapper.mapPageDto(orderService.getOrdersHistory(userId, params));
        return PagedModel.of(orderResponseDtoPage.getPage().stream()
                .map(orderModelAssembler::toModel)
                .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(orderResponseDtoPage));
    }

    @GetMapping("/orders")
    public PagedModel<OrderModel> getOrdersHistory(
            Authentication authentication,
            @RequestParam Map<String, String> params) {
        User user = userService.getUserByEmail(authentication.getName());
        Page<OrderResponseDto> orderResponseDtoPage =
                orderMapper.mapPageDto(orderService.getOrdersHistory(user.getId(), params));
        return PagedModel.of(orderResponseDtoPage.getPage().stream()
                        .map(orderModelAssembler::toModel)
                        .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(orderResponseDtoPage));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/top-tag")
    public PagedModel<TagModel> getTopTagByUserId(@PathVariable BigInteger userId,
                                          @RequestParam Map<String, String> params) {
        User user = userService.get(userId);
        Page<TagResponseDto> tagResponseDtoPage =
                tagMapper.mapPageDto(tagService.getTopTag(user, params));
        return PagedModel.of(tagResponseDtoPage.getPage().stream()
                        .map(tagModelAssembler::toModel)
                        .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(tagResponseDtoPage));
    }

    @GetMapping("/top-tag")
    public PagedModel<TagModel> getTopTag(Authentication authentication,
                                          @RequestParam Map<String, String> params) {
        User user = userService.getUserByEmail(authentication.getName());
        Page<TagResponseDto> tagResponseDtoPage =
                tagMapper.mapPageDto(tagService.getTopTag(user, params));
        return PagedModel.of(tagResponseDtoPage.getPage().stream()
                        .map(tagModelAssembler::toModel)
                        .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(tagResponseDtoPage));
    }
}
