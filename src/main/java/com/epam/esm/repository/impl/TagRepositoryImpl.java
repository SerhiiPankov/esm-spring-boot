package com.epam.esm.repository.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.model.Order;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.TagRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {

    public TagRepositoryImpl(SessionFactory factory) {
        super(factory, Tag.class, null);
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
    public Page<Tag> getHiQualityTag(User user, Pagination pagination) {
        String sqlMaxCount = """
                SELECT MAX(frequency) AS maximum FROM (
                SELECT COUNT(t.name) AS frequency FROM Tag t
                JOIN t.giftCertificates gc
                JOIN gc.orders o
                JOIN o.user u
                WHERE o.user = :user
                GROUP BY t.name)""";
        String sqlFrequentlyUsedTags = """
                SELECT t FROM Tag t
                JOIN t.giftCertificates gc
                JOIN gc.orders o
                JOIN o.user u
                WHERE o.user = :user
                GROUP BY t.name
                HAVING COUNT(t.name) = :maxCount""";
        String sqlMaxPriceOrders = """
                SELECT o FROM Order o
                JOIN o.giftCertificates gc
                JOIN gc.tags t
                WHERE t in :frequentlyUsedTags
                AND o.totalPrice = (SELECT MAX(o.totalPrice) FROM Order o
                JOIN o.giftCertificates gc
                JOIN gc.tags t
                WHERE t IN :frequentlyUsedTags)""";
        String sql = """
                SELECT t FROM Tag t
                JOIN t.giftCertificates gc
                JOIN gc.orders o
                WHERE t IN :frequentlyUsedTags AND o IN :maxPriceOrders""";
        try (Session session = factory.openSession()) {
            Long maxCount = session.createQuery(sqlMaxCount, Long.class)
                    .setParameter("user", user)
                    .getSingleResult();
            List<Tag> frequentlyUsedTags = session.createQuery(sqlFrequentlyUsedTags, Tag.class)
                    .setParameter("user", user)
                    .setParameter("maxCount", maxCount)
                    .getResultList();
            List<Order> maxPriceOrders = session.createQuery(sqlMaxPriceOrders, Order.class)
                    .setParameter("frequentlyUsedTags", frequentlyUsedTags)
                    .getResultList();
            List<Tag> hiQualityTags = session.createQuery(sql, Tag.class)
                    .setParameter("frequentlyUsedTags", frequentlyUsedTags)
                    .setParameter("maxPriceOrders", maxPriceOrders)
                    .getResultList();
            return new Page<>(hiQualityTags.subList(
                    pagination.getPage() - 1,
                    hiQualityTags.size() > pagination.getCount()
                            ? pagination.getPage() * pagination.getCount()
                            : hiQualityTags.size()),
                    (long) hiQualityTags.size(),pagination.getPage(), pagination.getCount());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProcessingException("Cannot get hi-quality tag by user " + user);
        }
    }
}
