package com.epam.esm.repository;

import com.epam.esm.model.Role;
import java.util.Optional;

public interface RoleRepository {
    Role create(Role role);

    Optional<Role> getByName(String roleName);
}
