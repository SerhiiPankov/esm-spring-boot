package com.epam.esm.controller.repository;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TestGiftCertificateRepository {
    @Autowired
    SessionFactory factory;

    @Autowired
    GiftCertificateMapper giftCertificateMapper;

    public GiftCertificate addCertificate(String name, String description, int duration,
                                          BigDecimal price, List<String> tags) {
        GiftCertificate giftCertificate = createGiftCertificate(name, description, duration, price, tags);
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(giftCertificate);
        transaction.commit();
        session.close();
        return giftCertificate;
    }

    private GiftCertificate createGiftCertificate(String name, String description,
                                                  int duration, BigDecimal price,
                                                  List<String> tags) {
        GiftCertificateRequestDto dto = new GiftCertificateRequestDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setDuration(duration);
        dto.setPrice(price);
        dto.setTags(tags);
        return giftCertificateMapper.mapToGiftCertificate(dto);
    }
}
