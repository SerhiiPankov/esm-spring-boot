package com.epam.esm.controller.repository;

import com.epam.esm.model.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestTagRepository {
    @Autowired
    SessionFactory factory;

    public Tag addTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(tag);
        transaction.commit();
        session.close();
        return tag;
    }
}
