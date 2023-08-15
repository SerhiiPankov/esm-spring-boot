package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.model.Role;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        return roleRepository.create(role);
    }

    @Override
    public Role getByName(String roleName) {
        return roleRepository.getByName(roleName)
                .orElseThrow(
                        () -> new DataProcessingException("Can't get role by name " + roleName));
    }
}
