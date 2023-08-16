package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.dto.GiftCertificateResponseDto;
import com.epam.esm.lib.data.Page;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
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
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;
    private final GiftCertificateMapper giftCertificateMapper;

    public GiftCertificateController(GiftCertificateService certificateService,
                                     GiftCertificateMapper giftCertificateMapper) {
        this.certificateService = certificateService;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @PostMapping
    public GiftCertificateResponseDto create(@RequestBody GiftCertificateRequestDto requestDto)
            throws ReflectiveOperationException {
        GiftCertificate giftCertificate =
                certificateService.create(giftCertificateMapper.mapToGiftCertificate(requestDto));
        return giftCertificateMapper.mapToGiftCertificateResponseDto(giftCertificate);
    }

    @PutMapping("/{id}")
    public GiftCertificateResponseDto update(@PathVariable BigInteger id,
                                             @RequestBody GiftCertificateRequestDto requestDto)
            throws ReflectiveOperationException {
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificatePartOfField(
                certificateService.get(id), requestDto);
        return giftCertificateMapper.mapToGiftCertificateResponseDto(
                certificateService.update(giftCertificate));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable BigInteger id) {
        certificateService.delete(id);
    }

    @GetMapping("/{id}")
    public GiftCertificateResponseDto get(@PathVariable BigInteger id) {
        return giftCertificateMapper.mapToGiftCertificateResponseDto(certificateService.get(id));
    }

    @GetMapping
    public Page<GiftCertificateResponseDto> getAll(@RequestParam Map<String, String> params) {
        return giftCertificateMapper.mapPageDto(certificateService.getAllByParameters(params));
    }
}
