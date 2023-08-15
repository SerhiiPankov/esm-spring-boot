package com.epam.esm.controller;

import com.epam.esm.dto.MessageResponseDto;
import com.epam.esm.dto.ShoppingCartResponseDto;
import com.epam.esm.mapper.MessageMapper;
import com.epam.esm.mapper.ShoppingCartMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.ShoppingCartService;
import com.epam.esm.service.UserService;
import java.math.BigInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final GiftCertificateService giftCertificateService;
    private final UserService userService;
    private final MessageMapper messageMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  GiftCertificateService giftCertificateService,
                                  UserService userService,
                                  MessageMapper messageMapper,
                                  ShoppingCartMapper shoppingCartMapper) {
        this.shoppingCartService = shoppingCartService;
        this.giftCertificateService = giftCertificateService;
        this.userService = userService;
        this.messageMapper = messageMapper;
        this.shoppingCartMapper = shoppingCartMapper;
    }

    @PutMapping("/gift-certificates")
    public MessageResponseDto addGiftCertificateToCart(@RequestParam BigInteger giftCertificateId,
                                                       @RequestParam BigInteger userId) {
        User user = userService.get(userId);
        GiftCertificate giftCertificate = giftCertificateService.get(giftCertificateId);
        shoppingCartService.addGiftCertificate(giftCertificate, user);
        return messageMapper.mapToTagResponseDto("Gift certificate was added to shopping cart");
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto get(@RequestParam BigInteger userId) {
        User user = userService.get(userId);
        return shoppingCartMapper.mapToDto(shoppingCartService.getByUser(user));
    }
}

