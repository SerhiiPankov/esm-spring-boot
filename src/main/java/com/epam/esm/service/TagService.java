package com.epam.esm.service;

import com.epam.esm.model.Tag;

import java.math.BigInteger;
import java.util.List;

public interface TagService {
    Tag create(Tag tag);

    Tag update(Tag tag);

    void delete(BigInteger id);

    Tag get(BigInteger id);

    List<Tag> getAll();

    List<Tag> getByNames(List<String> names);
}
