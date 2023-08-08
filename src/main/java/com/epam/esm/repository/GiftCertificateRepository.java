package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate update(GiftCertificate giftCertificate);

    void delete(Object id);

    Optional<GiftCertificate> get(Object id);

    void addTags(GiftCertificate giftCertificate);

    List<GiftCertificate> getAllByParameters(String spec);
}
