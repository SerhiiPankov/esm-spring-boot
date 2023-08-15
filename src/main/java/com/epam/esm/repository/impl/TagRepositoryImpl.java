package com.epam.esm.repository.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.TagRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {
    public TagRepositoryImpl(SessionFactory factory) {
        super(factory, Tag.class);
    }

    @Override
    public List<Tag> getByNames(List<String> names) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
            Root<Tag> root = query.from(Tag.class);
            CriteriaBuilder.In<String> predicate =
                    criteriaBuilder.in(root.get("name"));
            for (String name: names) {
                predicate.value(name);
            }
            query.where(predicate);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get tags by names " + names + e);
        }
    }

    @Override
    public long count() {
        try (Session session = factory.openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(*) FROM Tag t", Long.class);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new DataProcessingException("Cannot count tags");
        }
    }

    @Override
    public Optional<Tag> getHiQualityTag(User user) {
        try (Session session = factory.openSession()) {
            Query<Tag> query = session.createQuery(
                    " FROM Tag", Tag.class);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Cannot get hi-quality tag by user " + user);
        }
    }
}
