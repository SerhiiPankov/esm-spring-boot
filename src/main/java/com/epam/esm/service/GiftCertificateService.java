package com.epam.esm.service;

import com.epam.esm.lib.data.Page;
import com.epam.esm.model.GiftCertificate;
import java.math.BigInteger;
import java.util.Map;

public interface GiftCertificateService {
    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate update(GiftCertificate giftCertificate);

    void delete(BigInteger id);

    GiftCertificate get(BigInteger id);

    Page<GiftCertificate> getAllByParameters(Map<String, String> params);
}
