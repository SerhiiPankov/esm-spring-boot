package com.epam.esm.controller;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.dto.TagResponseDto;
import com.epam.esm.lib.data.Page;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import java.math.BigInteger;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final UserService userService;
    private final TagService tagService;
    private final TagMapper tagMapper;

    public TagController(UserService userService, TagService tagService, TagMapper tagMapper) {
        this.userService = userService;
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @PostMapping
    public TagResponseDto create(@RequestBody TagRequestDto requestDto)
            throws ReflectiveOperationException {
        Tag tag = tagService.create(tagMapper.mapToTag(requestDto));
        return tagMapper.mapToTagResponseDto(tag);
    }

    @PutMapping("/{id}")
    public TagResponseDto update(@PathVariable BigInteger id,
                                 @RequestBody TagRequestDto requestDto)
            throws ReflectiveOperationException {
        Tag tag = tagMapper.mapToTag(requestDto);
        tag.setId(id);
        Tag updatedTag = tagService.update(tag);
        return tagMapper.mapToTagResponseDto(updatedTag);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable BigInteger id) {
        tagService.delete(id);
    }

    @GetMapping("/{id}")
    public TagResponseDto get(@PathVariable BigInteger id) {
        return tagMapper.mapToTagResponseDto(tagService.get(id));
    }

    @GetMapping
    public Page<TagResponseDto> getAll(@RequestParam Map<String, String> params) {


        return tagMapper.mapPageDto(tagService.getAll(params));
    }

    @GetMapping("/hi-quality-tag")
    public TagResponseDto hiQualityTagByUser(@RequestParam BigInteger userId) {
        User user = userService.get(userId);
        Tag tag = tagService.getHiQualityTag(user);
        return tagMapper.mapToTagResponseDto(tag);
    }
}
