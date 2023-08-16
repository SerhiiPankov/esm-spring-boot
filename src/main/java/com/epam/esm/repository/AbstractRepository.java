package com.epam.esm.repository;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.lib.data.Page;
import com.epam.esm.lib.data.Pagination;
import com.epam.esm.lib.data.Parameter;
import com.epam.esm.lib.data.Sort;
import com.epam.esm.specification.SpecificationManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractRepository<T> {
    protected final SessionFactory factory;
    private final Class<T> clazz;
    private final SpecificationManager<T> specificationManager;

    public AbstractRepository(SessionFactory factory,
                              Class<T> clazz,
                              SpecificationManager<T> specificationManager) {
        this.factory = factory;
        this.clazz = clazz;
        this.specificationManager = specificationManager;
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

    public Page<T> getAll(List<Parameter> filterParams, Pagination pagination) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> queryFilter = cb.createQuery(clazz);
            Root<T> rootFilter = queryFilter.from(clazz);
            setFilterParameter(filterParams, cb, rootFilter);
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<T> rootCount = countQuery.from(clazz);
            countQuery.select(cb.count(rootCount));
            if (filterParams.size() > 0) {
                Predicate predicateFilter = setFilterParameter(filterParams, cb, rootFilter);
                queryFilter.where(predicateFilter);
                Predicate predicateCount = setFilterParameter(filterParams, cb, rootCount);
                countQuery.where(predicateCount);
            }
            Long count = session.createQuery(countQuery).getSingleResult();
            setSort(cb, queryFilter, rootFilter, pagination);
            CriteriaQuery<T> selectFilter = queryFilter.select(rootFilter);
            return new Page<>(getTypedQuery(session, selectFilter, pagination).getResultList(),
                    count, pagination.getPage(), pagination.getCount());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProcessingException("Can't get all "
                    + clazz.getSimpleName() + "s from db");
        }
    }

    private Predicate setFilterParameter(List<Parameter> filterParams,
                                         CriteriaBuilder cb, Root<T> root) {
        Predicate predicate = cb.conjunction();
        for (Parameter parameter: filterParams) {
            predicate = cb.and(specificationManager.get(parameter.getName(), parameter.getValues(),
                    root, cb), predicate);
        }
        return predicate;
    }

    private void setSort(CriteriaBuilder cb, CriteriaQuery<T> query,
                         Root<T> root, Pagination pagination) {
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

    private TypedQuery<T> getTypedQuery(Session session,
                                        CriteriaQuery<T> select,
                                        Pagination pagination) {
        TypedQuery<T> typedQuery = session.createQuery(select);
        typedQuery.setFirstResult(pagination.getCount() * pagination.getPage()
                - pagination.getCount());
        typedQuery.setMaxResults(pagination.getCount());
        return typedQuery;
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
