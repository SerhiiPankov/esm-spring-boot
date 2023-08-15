package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.ShoppingCart;
import com.epam.esm.model.User;

public interface ShoppingCartService {
    void addGiftCertificate(GiftCertificate giftCertificate, User user);

    ShoppingCart getByUser(User user);

    void registerNewShoppingCart(User user);

    void clear(ShoppingCart shoppingCart);
}
