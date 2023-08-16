package com.epam.esm.controller;

import com.epam.esm.dto.UserRequestDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.lib.data.Page;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.Role;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.UserService;
import jakarta.validation.Valid;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final RoleService roleService;
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(RoleService roleService, UserService userService, UserMapper userMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserResponseDto register(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userMapper.mapToDto(userService.register(
                userRequestDto.getEmail(), userRequestDto.getPassword(),
                Set.of(roleService.getByName(Role.RoleName.USER.name()))));
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable BigInteger id) {
        return userMapper.mapToDto(userService.get(id));
    }

    @GetMapping
    public Page<UserResponseDto> getAll(@RequestParam Map<String, String> params) {
        return userMapper.mapPageDto(userService.getAll(params));
    }
}
