package com.epam.esm.service.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.ShoppingCart;
import com.epam.esm.model.User;
import com.epam.esm.repository.ShoppingCartRepository;
import com.epam.esm.service.ShoppingCartService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void addGiftCertificate(GiftCertificate giftCertificate, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.getByUser(user);
        shoppingCart.getGiftCertificates().add(giftCertificate);
        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice().add(giftCertificate.getPrice()));
        shoppingCartRepository.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartRepository.getByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setTotalPrice(BigDecimal.ZERO);
        shoppingCartRepository.create(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.setGiftCertificates(null);
        shoppingCart.setTotalPrice(BigDecimal.ZERO);
        shoppingCartRepository.update(shoppingCart);
    }
}
