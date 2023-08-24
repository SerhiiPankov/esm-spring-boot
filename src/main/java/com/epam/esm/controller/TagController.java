package com.epam.esm.controller;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.dto.TagResponseDto;
import com.epam.esm.hateoas.PageMetadataParser;
import com.epam.esm.hateoas.assembler.MessageModelAssembler;
import com.epam.esm.hateoas.assembler.TagModelAssembler;
import com.epam.esm.hateoas.model.MessageModel;
import com.epam.esm.hateoas.model.TagModel;
import com.epam.esm.lib.data.Page;
import com.epam.esm.mapper.MessageMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.hateoas.PagedModel;
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
    private final MessageMapper messageMapper;
    private final TagMapper tagMapper;
    private final MessageModelAssembler messageModelAssembler;
    private final TagModelAssembler tagModelAssembler;
    private final PageMetadataParser pageMetadataParser;

    public TagController(UserService userService,
                         TagService tagService,
                         MessageMapper messageMapper,
                         TagMapper tagMapper,
                         MessageModelAssembler messageModelAssembler,
                         TagModelAssembler tagModelAssembler,
                         PageMetadataParser pageMetadataParser) {
        this.userService = userService;
        this.tagService = tagService;
        this.messageMapper = messageMapper;
        this.tagMapper = tagMapper;
        this.messageModelAssembler = messageModelAssembler;
        this.tagModelAssembler = tagModelAssembler;
        this.pageMetadataParser = pageMetadataParser;
    }

    @PostMapping
    public TagModel createTag(@RequestBody TagRequestDto requestDto) {
        Tag tag = tagService.create(tagMapper.mapToTag(requestDto));
        return tagModelAssembler.toModel(tagMapper.mapToTagResponseDto(tag));
    }

    @PutMapping("/{tagId}")
    public TagModel updateTag(@PathVariable BigInteger tagId,
                                 @RequestBody TagRequestDto requestDto) {

        return tagModelAssembler.toModel(
                tagMapper.mapToTagResponseDto(
                        tagService.update(tagId, tagMapper.mapToTag(requestDto))));
    }

    @DeleteMapping("/{tagId}")
    public MessageModel deleteTag(@PathVariable BigInteger tagId) {
        tagService.delete(tagId);
        return messageModelAssembler.toModel(
                messageMapper.mapToTagResponseDto("Tag with id " + tagId + " was deleted"));
    }

    @GetMapping("/{tagId}")
    public TagModel getTag(@PathVariable BigInteger tagId) {
        return tagModelAssembler.toModel(
                tagMapper.mapToTagResponseDto(tagService.get(tagId)));
    }

    @GetMapping
    public PagedModel<TagModel> getAllTags(@RequestParam Map<String, String> params) {
        Page<TagResponseDto> tagResponseDtoPage = tagMapper.mapPageDto(tagService.getAll(params));
        return PagedModel.of(tagResponseDtoPage.getPage().stream()
                .map(tagModelAssembler::toModel)
                .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(tagResponseDtoPage));
    }

    @GetMapping("/top-tag")
    public PagedModel<TagModel> getTopTag(@RequestParam BigInteger userId,
                                          @RequestParam Map<String, String> params) {
        User user = userService.get(userId);
        Page<TagResponseDto> tagResponseDtoPage =
                tagMapper.mapPageDto(tagService.getTopTag(user, params));
        return PagedModel.of(tagResponseDtoPage.getPage().stream()
                .map(tagModelAssembler::toModel)
                .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(tagResponseDtoPage));
    }
}
