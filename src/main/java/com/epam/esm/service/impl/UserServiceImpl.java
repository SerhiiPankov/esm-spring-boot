package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.ShoppingCartService;
import com.epam.esm.service.UserService;
import com.epam.esm.specification.PaginationAndSortingHandler;
import com.epam.esm.specification.ParameterParser;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final PaginationAndSortingHandler paginationAndSortingHandler;
    private final ParameterParser parameterParser;

    public UserServiceImpl(UserRepository userRepository,
                           ShoppingCartService shoppingCartService,
                           PaginationAndSortingHandler paginationAndSortingHandler,
                           ParameterParser parameterParser) {
        this.userRepository = userRepository;
        this.shoppingCartService = shoppingCartService;
        this.paginationAndSortingHandler = paginationAndSortingHandler;
        this.parameterParser = parameterParser;
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
        return userRepository.getAll(parameterParser.parseParameters(params),
                paginationAndSortingHandler.handle(params));
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
