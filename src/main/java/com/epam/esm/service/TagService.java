package com.epam.esm.service;

import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface TagService {
    Tag create(Tag tag);

    Tag update(BigInteger tagId, Tag tag);

    void delete(BigInteger id);

    Tag get(BigInteger id);

    Page<Tag> getAll(Map<String, String> params);

    List<Tag> getByNames(List<String> names);

    long count();

    Page<Tag> getTopTag(User user, Map<String, String> params);
}
