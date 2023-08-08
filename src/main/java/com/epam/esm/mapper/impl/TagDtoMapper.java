package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.dto.TagResponseDto;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDtoMapper implements DtoMapper<Tag, TagRequestDto, TagResponseDto> {
    @Override
    public Tag mapToModel(TagRequestDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        return tag;
    }

    @Override
    public TagResponseDto mapToDto(Tag tag) {
        TagResponseDto tagResponseDto = new TagResponseDto();
        tagResponseDto.setId(tag.getId());
        tagResponseDto.setName(tag.getName());
        return tagResponseDto;
    }
}
