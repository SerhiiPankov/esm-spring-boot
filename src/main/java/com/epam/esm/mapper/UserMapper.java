package com.epam.esm.mapper;

import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.lib.data.Page;
import com.epam.esm.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RoleMapper.class})
public abstract class UserMapper {
    @Autowired
    protected RoleMapper roleMapper;

    public abstract UserResponseDto mapToDto(User user);

    public abstract Page<UserResponseDto> mapPageDto(Page<User> page);
}
