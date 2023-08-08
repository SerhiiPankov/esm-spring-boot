package com.epam.esm.controller;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.dto.TagResponseDto;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final DtoMapper<Tag, TagRequestDto, TagResponseDto> dtoMapper;

    public TagController(TagService tagService,
                         DtoMapper<Tag, TagRequestDto, TagResponseDto> dtoMapper) {
        this.tagService = tagService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public TagResponseDto create(@RequestBody TagRequestDto requestDto)
            throws ReflectiveOperationException {
        Tag tag = tagService.create(dtoMapper.mapToModel(requestDto));
        return dtoMapper.mapToDto(tag);
    }

    @PutMapping("/{id}")
    public TagResponseDto update(@PathVariable BigInteger id,
                                 @RequestBody TagRequestDto requestDto)
            throws ReflectiveOperationException {
        Tag tag = dtoMapper.mapToModel(requestDto);
        tag.setId(id);
        Tag updatedTag = tagService.update(tag);
        return dtoMapper.mapToDto(updatedTag);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable BigInteger id) {
        tagService.delete(id);
    }

    @GetMapping("/{id}")
    public TagResponseDto get(@PathVariable BigInteger id) {
        return dtoMapper.mapToDto(tagService.get(id));
    }

    @GetMapping
    public List<TagResponseDto> getAll() {
        return tagService.getAll().stream().map(dtoMapper::mapToDto).collect(Collectors.toList());
    }
}
