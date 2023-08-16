package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.specification.SpecificationManager;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractRepository<GiftCertificate>
        implements GiftCertificateRepository {
    public GiftCertificateRepositoryImpl(
            SessionFactory factory, SpecificationManager<GiftCertificate> specificationManager) {
        super(factory, GiftCertificate.class, specificationManager);
    }
}
