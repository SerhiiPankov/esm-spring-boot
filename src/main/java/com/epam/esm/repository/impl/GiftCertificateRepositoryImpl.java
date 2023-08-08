package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractRepository<GiftCertificate>
        implements GiftCertificateRepository {

    public GiftCertificateRepositoryImpl(SessionFactory factory) {
        super(factory, GiftCertificate.class);
    }

    @Override
    public void addTags(GiftCertificate giftCertificate) {

    }

    @Override
    public List<GiftCertificate> getAllByParameters(String spec) {
        return null;
    }
}
