package com.epam.esm.hateoas.model;

import java.math.BigInteger;
import org.springframework.hateoas.RepresentationModel;

public class TagModel extends RepresentationModel<TagModel> {
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
