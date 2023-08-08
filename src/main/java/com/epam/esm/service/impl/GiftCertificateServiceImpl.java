package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.specification.PaginationAndSortingHandler;
import com.epam.esm.specification.SpecificationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final SpecificationManager specificationManager;
    private final PaginationAndSortingHandler paginationAndSortingHandler;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository,
                                      SpecificationManager specificationManager,
                                      PaginationAndSortingHandler paginationAndSortingHandler) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.specificationManager = specificationManager;
        this.paginationAndSortingHandler = paginationAndSortingHandler;
    }

    @Transactional
    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        LocalDateTime now = LocalDateTime.now();
        giftCertificate.setCreateDate(now);
        giftCertificate.setLastUpdateDate(now);
        GiftCertificate giftCertificateFromDb = giftCertificateRepository.create(giftCertificate);
//        giftCertificateRepository.addTags(giftCertificate);
        return giftCertificateFromDb;
    }

    @Transactional
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        if (giftCertificate.getTags() != null) {
            giftCertificateRepository.addTags(giftCertificate);
        }
        if (giftCertificateRepository.update(giftCertificate) == null) {
            throw new DataProcessingException("Can't update gift certificate " + giftCertificate);
        }
        GiftCertificate giftCertificateFromDb = giftCertificateRepository.get(giftCertificate.getId()).orElseThrow(
                () -> new DataProcessingException("Can't get gift certificate with id " + giftCertificate.getId()));
        giftCertificateFromDb.setTags(tagRepository.getTagsByGiftCertificateId(giftCertificate.getId()));
        return giftCertificateFromDb;
    }

    @Transactional
    @Override
    public void delete(BigInteger id) {
        tagRepository.deleteTagsByGiftCertificateId(id);
        giftCertificateRepository.delete(id);
    }

    @Transactional
    @Override
    public GiftCertificate get(BigInteger id) {
        GiftCertificate giftCertificate = giftCertificateRepository.get(id).orElseThrow(
                () -> new DataProcessingException("Can't get gift certificate with id " + id));
        giftCertificate.setTags(tagRepository.getTagsByGiftCertificateId(id));
        return giftCertificate;
    }

    @Transactional
    @Override
    public List<GiftCertificate> getAllByParameters(Map<String, String> params) {
        List<String> specificationIgnoreParams = new ArrayList<>();
        Collections.addAll(specificationIgnoreParams, paginationAndSortingHandler.getFields());
        StringJoiner specification = new StringJoiner(" AND ", " WHERE ", " ");
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (!specificationIgnoreParams.contains(param.getKey())) {
                specification.add(specificationManager.get(param.getKey(),
                        param.getValue().split(",")));
            }
        }
        List<GiftCertificate> allByParameters = giftCertificateRepository
                .getAllByParameters(specification + paginationAndSortingHandler.handle(params));
        allByParameters.forEach(
                c -> c.setTags(tagRepository.getTagsByGiftCertificateId(c.getId())));
        return allByParameters;
    }
}
