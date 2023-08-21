package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.dto.GiftCertificateResponseDto;
import com.epam.esm.lib.data.Page;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TagMapper.class})
public abstract class GiftCertificateMapper {
    @Autowired
    protected TagService tagService;
    @Autowired
    protected TagMapper tagMapper;
    @Autowired
    protected OrderMapper orderMapper;

    @Mapping(source = "tags", target = "tags", qualifiedByName = "mapTags")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "orders", ignore = true)
    public abstract GiftCertificate mapToGiftCertificate(GiftCertificateRequestDto dto);

    public abstract GiftCertificateResponseDto mapToGiftCertificateResponseDto(
            GiftCertificate model);

    public abstract Page<GiftCertificateResponseDto> mapPageDto(Page<GiftCertificate> page);

    @Named("mapTags")
    protected List<Tag> mapTags(List<String> tags) {
        if (tags == null) {
            return null;
        }
        List<Tag> tagsFromDb = tagService.getByNames(tags);
        List<String> tagsForAddToDb = tags.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        if (tagsFromDb.size() > 0) {
            tagsForAddToDb.removeAll(tagsFromDb.stream()
                    .map(Tag::getName)
                    .toList());
        }
        for (String name: tagsForAddToDb) {
            Tag tag = new Tag();
            tag.setName(name);
            tagsFromDb.add(tagService.create(tag));
        }
        return tagsFromDb;
    }

    public GiftCertificate mapToGiftCertificatePartOfField(
            GiftCertificate model, GiftCertificateRequestDto dto) {
        if (dto.getName() != null) {
            model.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            model.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            model.setPrice(dto.getPrice());
        }
        if (dto.getDuration() != null) {
            model.setDuration(dto.getDuration());
        }
        if (dto.getTags() != null) {
            model.setTags(mapTags(dto.getTags()));
        }
        return model;
    }
}
