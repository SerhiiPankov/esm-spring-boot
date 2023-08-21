package com.epam.esm.repository;

import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Parameter;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import java.util.List;
import java.util.Optional;

public interface TagRepository {
    Tag create(Tag tag);

    Tag update(Tag tag);

    void delete(Object id);

    Optional<Tag> get(Object id);

    Page<Tag> getAll(List<Parameter> filterParams, Pagination pagination);

    List<Tag> getByNames(List<String> names);

    long count();

    Page<Tag> getHiQualityTag(User user, Pagination pagination);
}
