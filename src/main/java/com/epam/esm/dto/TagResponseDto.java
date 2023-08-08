package com.epam.esm.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TagResponseDto {
    private BigInteger id;
    private String name;
}
