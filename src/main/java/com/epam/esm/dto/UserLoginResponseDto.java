package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLoginResponseDto {
    private String token;
}
