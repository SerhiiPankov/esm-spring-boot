package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Specification;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.ShoppingCartService;
import com.epam.esm.service.UserService;

import java.util.Map;
import java.util.Set;

import com.epam.esm.specification.PaginationAndSortingHandler;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final PaginationAndSortingHandler paginationAndSortingHandler;

    public UserServiceImpl(UserRepository userRepository,
                           ShoppingCartService shoppingCartService, PaginationAndSortingHandler paginationAndSortingHandler) {
        this.userRepository = userRepository;
        this.shoppingCartService = shoppingCartService;
        this.paginationAndSortingHandler = paginationAndSortingHandler;
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User get(Object id) {
        return userRepository.get(id).orElseThrow(
                () -> new DataProcessingException("Can't get user with id " + id));
    }

    @Override
    public Page<User> getAll(Map<String, String> params) {
        return userRepository.getAll(new Specification(), paginationAndSortingHandler.handle(params));
    }

    @Override
    public User register(String email, String password, Set<Role> roles) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }
}
