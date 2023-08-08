package com.epam.esm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GiftCertificateRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<String> tags;
}
