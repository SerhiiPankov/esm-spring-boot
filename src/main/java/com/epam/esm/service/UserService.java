package com.epam.esm.service;

import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User get(Object id);

    Page<User> getAll(Map<String, String> params);

    User register(String email, String password, Set<Role> roles);

    User getUserByEmail(String email);
}
