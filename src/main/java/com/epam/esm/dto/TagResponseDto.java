package com.epam.esm.dto;

import java.math.BigInteger;
import org.springframework.hateoas.RepresentationModel;

public class TagResponseDto extends RepresentationModel<TagResponseDto> {
    private BigInteger id;
    private String name;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
