package com.epam.esm.mapper;

import com.epam.esm.dto.RoleResponseDto;
import com.epam.esm.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "role.roleName", target = "roleName")
    RoleResponseDto mapToDto(Role role);
}
