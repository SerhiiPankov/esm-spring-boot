package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.dto.GiftCertificateResponseDto;
import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.dto.TagResponseDto;
import com.epam.esm.mapper.DtoMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftCertificateDtoMapper implements
        DtoMapper<GiftCertificate, GiftCertificateRequestDto, GiftCertificateResponseDto> {

    private final DtoMapper<Tag, TagRequestDto, TagResponseDto> tagDtoMapper;
    private final TagService tagService;

    public GiftCertificateDtoMapper(DtoMapper<Tag, TagRequestDto, TagResponseDto> tagDtoMapper,
                                    TagService tagService) {
        this.tagDtoMapper = tagDtoMapper;
        this.tagService = tagService;
    }

    @Override
    public GiftCertificate mapToModel(GiftCertificateRequestDto dto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(dto.getName());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());
        giftCertificate.setTags(dto.getTags().stream().map(Tag::new).toList());
        return giftCertificate;
    }

    @Override
    public GiftCertificateResponseDto mapToDto(GiftCertificate giftCertificate) {
        GiftCertificateResponseDto giftCertificateResponseDto = new GiftCertificateResponseDto();
        giftCertificateResponseDto.setId(giftCertificate.getId());
        giftCertificateResponseDto.setName(giftCertificate.getName());
        giftCertificateResponseDto.setDescription(giftCertificate.getDescription());
        giftCertificateResponseDto.setPrice(giftCertificate.getPrice());
        giftCertificateResponseDto.setDuration(giftCertificate.getDuration());
        giftCertificateResponseDto.setCreateDate(giftCertificate.getCreateDate());
        giftCertificateResponseDto.setLastUpdateDate(giftCertificate.getLastUpdateDate());
        giftCertificateResponseDto.setTags(giftCertificate.getTags().stream()
                .map(tagDtoMapper::mapToDto)
                .collect(Collectors.toList()));
        return giftCertificateResponseDto;
    }

    private List<Tag> getTags(List<String> tagNames) {
        if (tagNames == null) {
            return null;
        }
        List<Tag> tagsFromDb = tagService.getByNames(tagNames);
        tagNames.removeAll(tagsFromDb.stream().map(Tag::getName).toList());
        for (String name: tagNames) {
            Tag tag = new Tag();
            tag.setName(name);
            tagsFromDb.add(tagService.create(tag));
        }
        return tagsFromDb;
    }
}
