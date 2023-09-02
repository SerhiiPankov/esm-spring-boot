package com.epam.esm.hateoas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.MessageResponseDto;
import com.epam.esm.hateoas.model.MessageModel;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class MessageModelAssembler extends
        RepresentationModelAssemblerSupport<MessageResponseDto, MessageModel> {
    public MessageModelAssembler() {
        super(UserController.class, MessageModel.class);
    }

    @Override
    public @NotNull MessageModel toModel(@NotNull MessageResponseDto messageResponseDto) {
        MessageModel messageModel = new MessageModel();
        BeanUtils.copyProperties(messageResponseDto, messageModel);
        messageModel.add(linkTo(methodOn(TagController.class)
                .getAllTags(new HashMap<>())).withRel("all-tags"));
        messageModel.add(linkTo(methodOn(GiftCertificateController.class)
                .getAllCertificates(new HashMap<>())).withRel("all-certificates"));
        return messageModel;
    }
}
