package com.epam.esm.repository.impl;

import com.epam.esm.model.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
    public UserRepositoryImpl(SessionFactory factory) {
        super(factory, User.class);
    }
}
