package com.epam.esm.service.impl;

import com.epam.esm.exception.AuthenticationException;
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
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;
    private final PaginationAndSortingHandler paginationAndSortingHandler;
    private final ParameterParser parameterParser;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ShoppingCartService shoppingCartService,
                           PaginationAndSortingHandler paginationAndSortingHandler,
                           ParameterParser parameterParser,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.shoppingCartService = shoppingCartService;
        this.paginationAndSortingHandler = paginationAndSortingHandler;
        this.parameterParser = parameterParser;
        this.passwordEncoder = passwordEncoder;
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
        if (userRepository.getByEmail(email).isPresent()) {
            throw new AuthenticationException("Cannot register user.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        shoppingCartService.registerNewShoppingCart(user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email).orElseThrow(
                () -> new DataProcessingException("Can't get user with email " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Not found user with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(
                                "ROLE_" + role.getRoleName().name()))
                        .collect(Collectors.toList())
        );
    }
}
