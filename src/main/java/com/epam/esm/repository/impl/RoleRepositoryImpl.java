package com.epam.esm.repository.impl;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.model.Role;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.RoleRepository;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {
    public RoleRepositoryImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getByName = session.createQuery(
                    "FROM Role WHERE roleName = :role", Role.class);
            getByName.setParameter("role", Role.RoleName.valueOf(roleName));
            return getByName.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Role with role name " + roleName + " not found");
        }
    }
}
