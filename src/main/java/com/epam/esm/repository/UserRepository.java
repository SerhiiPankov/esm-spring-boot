package com.epam.esm.repository;

import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Parameter;
import com.epam.esm.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User create(User user);

    Optional<User> get(Object id);

    Page<User> getAll(List<Parameter> filterParams, Pagination pagination);

    Optional<User> getByEmail(String email);
}
