package com.epam.esm.repository;

import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Parameter;
import com.epam.esm.model.GiftCertificate;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    GiftCertificate create(GiftCertificate giftCertificate);

    GiftCertificate update(GiftCertificate giftCertificate);

    void delete(Object id);

    Optional<GiftCertificate> get(Object id);

    Page<GiftCertificate> getAll(List<Parameter> filterParams, Pagination pagination);
}
