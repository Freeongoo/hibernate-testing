package org.example.repository.inheritance.no_inhericance.impl;

import org.example.entity.inheritance.no_inheritance.UserNoInheritance;
import org.example.repository.AbstractRepository;
import org.example.repository.inheritance.no_inhericance.UserNoInheritanceRepository;
import org.hibernate.query.Query;

import java.util.List;

public class UserNoInheritanceRepositoryImpl extends AbstractRepository<UserNoInheritance, Integer> implements UserNoInheritanceRepository {

    @Override
    public void delete(UserNoInheritance userNoInheritance) {
        UserNoInheritance persistentInstance = get(userNoInheritance.getId());
        getSession().delete(persistentInstance);
    }

    @Override
    public void update(UserNoInheritance userNoInheritance) {
        getSession().merge(userNoInheritance);
    }

    @Override
    public Integer create(UserNoInheritance userNoInheritance) {
        return (Integer) getSession().save(userNoInheritance);
    }

    @Override
    public UserNoInheritance get(Integer integer) {
        return getSession().get(UserNoInheritance.class, integer);
    }

    @Override
    public List<UserNoInheritance> getAll() {
        Query<UserNoInheritance> query = getSession().createQuery("from UserNoInheritance", UserNoInheritance.class);
        return query.list();
    }

    @Override
    public void deleteAll() {
        Query query = getSession().createQuery("delete from UserNoInheritance");
        query.executeUpdate();
    }
}
