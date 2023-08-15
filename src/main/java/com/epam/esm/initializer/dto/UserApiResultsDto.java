package com.epam.esm.initializer.dto;

import java.util.Objects;

public class UserApiResultsDto {
    private String email;
    private UserApiLoginInfoDto login;

    public String getEmail() {
        return email;
    }

    public UserApiLoginInfoDto getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserApiResultsDto that = (UserApiResultsDto) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
