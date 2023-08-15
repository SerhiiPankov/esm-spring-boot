package com.epam.esm.mapper;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.dto.TagResponseDto;
import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "giftCertificates", ignore = true)
    @Mapping(source = "name", target = "name", qualifiedByName = "toLowerCase")
    Tag mapToTag(TagRequestDto dto);

    @Named("toLowerCase")
    default String toLowerCase(String name) {
        return name.toLowerCase();
    }

    TagResponseDto mapToTagResponseDto(Tag model);

    Page<TagResponseDto> mapPageDto(Page<Tag> page);
}
