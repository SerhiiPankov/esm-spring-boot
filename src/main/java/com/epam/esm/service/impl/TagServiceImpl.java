package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public Tag create(Tag tag) {
        return tagRepository.create(tag);
    }

    @Transactional
    @Override
    public Tag update(Tag tag) {
        if (tagRepository.update(tag) == null) {
            throw new DataProcessingException("Can't update tag " + tag);
        }
        return get(tag.getId());
    }

    @Override
    public void delete(BigInteger id) {
        tagRepository.delete(id);
    }

    @Override
    public Tag get(BigInteger id) {
        return tagRepository.get(id).orElseThrow(
                () -> new DataProcessingException("Can't get tag with id " + id));
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.getAll();
    }

    @Override
    public List<Tag> getByNames(List<String> names) {
        return tagRepository.getByNames(names);
    }
}
