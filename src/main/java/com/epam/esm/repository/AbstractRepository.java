package com.epam.esm.repository;

import com.epam.esm.exception.DataProcessingException;

import java.util.List;
import java.util.Optional;

import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Sort;
import com.epam.esm.lib.data.Specification;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public abstract class AbstractRepository<T> {
    protected final SessionFactory factory;
    private final Class<T> clazz;

    public AbstractRepository(SessionFactory factory, Class<T> clazz) {
        this.factory = factory;
        this.clazz = clazz;
    }

    public T create(T t) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert "
                    + clazz.getSimpleName() + " " + t);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Optional<T> get(Object id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(clazz, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get "
                    + clazz.getSimpleName() + ", id: " + id);
        }
    }

    public Page<T> getAll(Specification specification, Pagination pagination) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(clazz);
            Root<T> root = query.from(clazz);
            CriteriaQuery<T> select = query.select(root);
            setSort(cb, query, root, pagination);
            return new Page<>(getTypedQuery(session, select,pagination).getResultList(),
                    getCount(session, cb), pagination.getPage(), pagination.getCount());
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all "
                    + clazz.getSimpleName() + "s from db");
        }
    }

    private void setSort(CriteriaBuilder cb, CriteriaQuery<T> query, Root<T> root, Pagination pagination) {
        if (pagination.getSortBy().size() > 0) {
            List<Sort> sortBy = pagination.getSortBy();
            Order[] orders = new Order[sortBy.size()];
            for (int i = 0; i < sortBy.size(); i++) {
                if (sortBy.get(i).getDirection().equals(Sort.Direction.ASC)) {
                    orders[i] = cb.asc(root.get(sortBy.get(i).getParameter()));
                } else {
                    orders[i] = cb.desc(root.get(sortBy.get(i).getParameter()));
                }
            }
            query.orderBy(orders);
        }
    }

    private TypedQuery<T> getTypedQuery(Session session, CriteriaQuery<T> select, Pagination pagination) {
        TypedQuery<T> typedQuery = session.createQuery(select);
        typedQuery.setFirstResult(pagination.getCount() * pagination.getPage() - pagination.getCount());
        typedQuery.setMaxResults(pagination.getCount());
        return typedQuery;
    }

    private Long getCount(Session session, CriteriaBuilder cb) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(clazz)));
        return session.createQuery(countQuery).getSingleResult();
    }


    public T update(T t) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.merge(t);
            transaction.commit();
            return t;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update "
                    + clazz.getSimpleName() + " " + t);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void delete(Object id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            T movieSession = session.get(clazz, id);
            session.remove(movieSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete "
                    + clazz.getSimpleName() + " with id: " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
