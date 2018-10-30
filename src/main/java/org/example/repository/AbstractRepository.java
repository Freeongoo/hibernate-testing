package org.example.repository;

import org.example.persistance.SessionHolder;
import org.hibernate.Session;

public abstract class AbstractRepository<T, ID> implements Repository<T, ID> {
    @Override
    public void flush() {
        getSession().flush();
    }

    protected Session getSession() {
        return SessionHolder.get();
    }
}
