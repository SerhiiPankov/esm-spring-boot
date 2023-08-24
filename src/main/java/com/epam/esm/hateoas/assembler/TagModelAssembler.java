package com.epam.esm.hateoas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagResponseDto;
import com.epam.esm.hateoas.model.TagModel;
import java.util.HashMap;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TagModelAssembler extends
        RepresentationModelAssemblerSupport<TagResponseDto, TagModel> {
    public TagModelAssembler() {
        super(TagController.class, TagModel.class);
    }

    @Override
    public TagModel toModel(TagResponseDto tagResponseDto) {
        TagModel tagModel = new TagModel();
        BeanUtils.copyProperties(tagResponseDto, tagModel);
        tagModel.add(linkTo(methodOn(TagController.class)
                .getTag(tagResponseDto.getId())).withSelfRel());
        tagModel.add(linkTo(methodOn(TagController.class)
                .getAllTags(new HashMap<>())).withRel("all-tags"));
        return tagModel;
    }
}
