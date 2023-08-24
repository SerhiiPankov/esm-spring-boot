package com.epam.esm.hateoas.model;

import com.epam.esm.dto.RoleResponseDto;
import java.math.BigInteger;
import java.util.Set;
import org.springframework.hateoas.RepresentationModel;

public class UserModel extends RepresentationModel<UserModel> {
    private BigInteger id;
    private String email;
    private Set<RoleResponseDto> roles;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleResponseDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleResponseDto> roles) {
        this.roles = roles;
    }
}
