package com.epam.esm.repository;

import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Specification;
import com.epam.esm.model.User;

import java.util.Optional;

public interface UserRepository {
    User create(User user);

    Optional<User> get(Object id);

    Page<User> getAll(Specification specification, Pagination pagination);
}
