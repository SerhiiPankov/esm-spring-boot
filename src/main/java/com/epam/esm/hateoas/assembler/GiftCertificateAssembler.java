package com.epam.esm.hateoas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateResponseDto;
import com.epam.esm.hateoas.model.GiftCertificateModel;
import java.util.HashMap;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateAssembler extends
        RepresentationModelAssemblerSupport<GiftCertificateResponseDto, GiftCertificateModel> {
    public GiftCertificateAssembler() {
        super(GiftCertificateController.class, GiftCertificateModel.class);
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificateResponseDto giftCertificateResponseDto) {
        GiftCertificateModel giftCertificateModel = new GiftCertificateModel();
        BeanUtils.copyProperties(giftCertificateResponseDto, giftCertificateModel);
        giftCertificateModel.add(linkTo(methodOn(GiftCertificateController.class)
                .getCertificateById(giftCertificateResponseDto.getId())).withSelfRel());
        giftCertificateModel.add(linkTo(methodOn(GiftCertificateController.class)
                .getAllCertificates(new HashMap<>())).withRel("all-certificates"));
        return giftCertificateModel;
    }
}
