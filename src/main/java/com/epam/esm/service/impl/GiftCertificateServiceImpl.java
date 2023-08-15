package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.specification.PaginationAndSortingHandler;
import com.epam.esm.specification.SpecificationManager;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.hibernate.ScrollableResults;
import org.springframework.stereotype.Service;

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

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        LocalDateTime now = LocalDateTime.now();
        giftCertificate.setCreateDate(now);
        giftCertificate.setLastUpdateDate(now);
        return giftCertificateRepository.create(giftCertificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return giftCertificateRepository.update(giftCertificate);
    }

    @Override
    public void delete(BigInteger id) {
//        tagRepository.deleteTagsByGiftCertificateId(id);
        giftCertificateRepository.delete(id);
    }

    @Override
    public GiftCertificate get(BigInteger id) {
        return giftCertificateRepository.get(id).orElseThrow(
                () -> new DataProcessingException("Can't get gift certificate with id " + id));
    }

    @Override
    public List<GiftCertificate> getAllByParameters(Map<String, String> params) {


        throw new RuntimeException("Method not implemented "
                + "GiftCertificateServiceImpl - getAllByParameters");
    }
}
