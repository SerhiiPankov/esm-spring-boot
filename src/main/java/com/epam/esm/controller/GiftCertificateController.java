package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.dto.GiftCertificateResponseDto;
import com.epam.esm.hateoas.PageMetadataParser;
import com.epam.esm.hateoas.assembler.GiftCertificateAssembler;
import com.epam.esm.hateoas.assembler.MessageModelAssembler;
import com.epam.esm.hateoas.model.GiftCertificateModel;
import com.epam.esm.hateoas.model.MessageModel;
import com.epam.esm.lib.data.Page;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.MessageMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
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
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;
    private final GiftCertificateMapper giftCertificateMapper;
    private final MessageMapper messageMapper;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final MessageModelAssembler messageModelAssembler;
    private final PageMetadataParser pageMetadataParser;

    public GiftCertificateController(GiftCertificateService certificateService,
                                     GiftCertificateMapper giftCertificateMapper,
                                     MessageMapper messageMapper,
                                     GiftCertificateAssembler giftCertificateAssembler,
                                     MessageModelAssembler messageModelAssembler,
                                     PageMetadataParser pageMetadataParser) {
        this.certificateService = certificateService;
        this.giftCertificateMapper = giftCertificateMapper;
        this.messageMapper = messageMapper;
        this.giftCertificateAssembler = giftCertificateAssembler;
        this.messageModelAssembler = messageModelAssembler;
        this.pageMetadataParser = pageMetadataParser;
    }

    @PostMapping
    public GiftCertificateModel createCertificate(
            @RequestBody GiftCertificateRequestDto requestDto) {
        GiftCertificate giftCertificate =
                certificateService.create(giftCertificateMapper.mapToGiftCertificate(requestDto));
        return giftCertificateAssembler.toModel(
                giftCertificateMapper.mapToGiftCertificateResponseDto(giftCertificate));
    }

    @PutMapping("/{giftCertificateId}")
    public GiftCertificateModel updateCertificate(@PathVariable BigInteger giftCertificateId,
                                             @RequestBody GiftCertificateRequestDto requestDto) {
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificatePartOfField(
                certificateService.get(giftCertificateId), requestDto);
        return giftCertificateAssembler.toModel(
                giftCertificateMapper.mapToGiftCertificateResponseDto(
                certificateService.update(giftCertificate)));
    }

    @DeleteMapping("/{giftCertificateId}")
    public MessageModel deleteCertificate(@PathVariable BigInteger giftCertificateId) {
        certificateService.delete(giftCertificateId);
        return messageModelAssembler.toModel(
                messageMapper.mapToTagResponseDto("Gift certificate with id "
                        + giftCertificateId + " was deleted"));
    }

    @GetMapping("/{giftCertificateId}")
    public GiftCertificateModel getCertificateById(@PathVariable BigInteger giftCertificateId) {
        return giftCertificateAssembler.toModel(
                giftCertificateMapper.mapToGiftCertificateResponseDto(
                        certificateService.get(giftCertificateId)));
    }

    @GetMapping
    public PagedModel<GiftCertificateModel> getAllCertificates(
            @RequestParam Map<String, String> params) {
        Page<GiftCertificateResponseDto> giftCertificateResponseDtoPage =
                giftCertificateMapper.mapPageDto(certificateService.getAllByParameters(params));
        return PagedModel.of(giftCertificateResponseDtoPage.getPage().stream()
                .map(giftCertificateAssembler::toModel)
                .collect(Collectors.toList()),
                pageMetadataParser.getPageMetadata(giftCertificateResponseDtoPage));
    }
}
