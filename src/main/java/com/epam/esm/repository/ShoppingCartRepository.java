package com.epam.esm.repository;

import com.epam.esm.model.ShoppingCart;
import com.epam.esm.model.User;

public interface ShoppingCartRepository {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart getByUser(User user);

    ShoppingCart update(ShoppingCart shoppingCart);
}
