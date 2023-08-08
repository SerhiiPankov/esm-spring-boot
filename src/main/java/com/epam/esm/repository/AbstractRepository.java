package com.epam.esm.repository;

import com.epam.esm.exception.DataProcessingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

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
            session.merge(t);
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

    public List<T> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from " + clazz.getSimpleName(), clazz).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all "
                    + clazz.getSimpleName() + "s from db");
        }
    }

    public T update(T t) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(t);
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
            session.delete(movieSession);
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
