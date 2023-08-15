package com.epam.esm.service;

import com.epam.esm.model.Role;

public interface RoleService {
    Role create(Role role);

    Role getByName(String roleName);
}
