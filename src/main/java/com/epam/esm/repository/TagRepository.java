package com.epam.esm.repository;

import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Specification;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Tag create(Tag tag);

    Tag update(Tag tag);

    void delete(Object id);

    Optional<Tag> get(Object id);

    Page<Tag> getAll(Specification specification, Pagination pagination);

    List<Tag> getByNames(List<String> names);

    long count();

    Optional<Tag> getHiQualityTag(User user);
}
