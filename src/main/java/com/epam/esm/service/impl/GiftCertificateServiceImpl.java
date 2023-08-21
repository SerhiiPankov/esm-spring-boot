package com.epam.esm.service.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.lib.data.Page;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.specification.PaginationAndSortingHandler;
import com.epam.esm.specification.ParameterParser;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final PaginationAndSortingHandler paginationAndSortingHandler;
    private final ParameterParser parameterParser;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      PaginationAndSortingHandler paginationAndSortingHandler,
                                      ParameterParser parameterParser) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.paginationAndSortingHandler = paginationAndSortingHandler;
        this.parameterParser = parameterParser;
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
        giftCertificateRepository.delete(id);
    }

    @Override
    public GiftCertificate get(BigInteger id) {
        return giftCertificateRepository.get(id).orElseThrow(
                () -> new DataProcessingException("Can't get gift certificate with id " + id));
    }

    @Override
    public Page<GiftCertificate> getAllByParameters(Map<String, String> params) {
        return giftCertificateRepository.getAll(parameterParser.parseParameters(params),
                paginationAndSortingHandler.handle(params));
    }
}
