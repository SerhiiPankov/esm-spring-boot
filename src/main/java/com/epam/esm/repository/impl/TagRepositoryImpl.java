package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.TagRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {

    public TagRepositoryImpl(SessionFactory factory) {
        super(factory, Tag.class);
    }

    @Override
    public List<Tag> getByNames(List<String> names) {



        return null;
    }

    @Override
    public List<Tag> getTagsByGiftCertificateId(BigInteger giftCertificateId) {
        return null;
    }

    @Override
    public void deleteTagsByGiftCertificateId(BigInteger giftCertificateID) {

    }
}
