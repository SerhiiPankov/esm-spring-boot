package com.epam.esm.service;

import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;

import java.util.Map;
import java.util.Set;

public interface UserService {
    User create(User user);

    User get(Object id);

    Page<User> getAll(Map<String, String> params);

    User register(String email, String password, Set<Role> roles);
}
