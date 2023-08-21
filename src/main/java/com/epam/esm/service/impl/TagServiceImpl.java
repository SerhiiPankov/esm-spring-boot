package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.PaginationAndSortingHandler;
import com.epam.esm.specification.ParameterParser;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final PaginationAndSortingHandler paginationAndSortingHandler;
    private final ParameterParser parameterParser;

    public TagServiceImpl(TagRepository tagRepository,
                          PaginationAndSortingHandler paginationAndSortingHandler,
                          ParameterParser parameterParser) {
        this.tagRepository = tagRepository;
        this.paginationAndSortingHandler = paginationAndSortingHandler;
        this.parameterParser = parameterParser;
    }

    @Override
    public Tag create(Tag tag) {
        return tagRepository.create(tag);
    }

    @Override
    public Tag update(Tag tag) {
        return tagRepository.update(tag);
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
    public Page<Tag> getAll(Map<String, String> params) {
        return tagRepository.getAll(parameterParser.parseParameters(params),
                paginationAndSortingHandler.handle(params));
    }

    @Override
    public List<Tag> getByNames(List<String> names) {
        return tagRepository.getByNames(names);
    }

    @Override
    public long count() {
        return tagRepository.count();
    }

    @Override
    public Page<Tag> getTopTag(User user, Map<String, String> params) {
        return tagRepository.getHiQualityTag(user, paginationAndSortingHandler.handle(params));
    }
}
