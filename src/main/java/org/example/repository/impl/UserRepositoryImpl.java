package org.example.repository.impl;

import org.example.entity.old_style_xml.User;
import org.example.repository.AbstractRepository;
import org.example.repository.UserRepository;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<User, Integer> implements UserRepository {
    @Override
    public void delete(User user) {
        User persistentInstance = get(user.getId());
        getSession().delete(persistentInstance);
    }

    @Override
    public void update(User user) {
        getSession().merge(user);
    }

    @Override
    public Integer create(User user) {
        return (Integer) getSession().save(user);
    }

    @Override
    public User get(Integer id) {
        return getSession().get(User.class, id);
    }

    @Override
    public User getByUserName(String userName) {
        Query query = getSession().createQuery("from User where userName = :userName");
        query.setParameter("userName", userName);
        return (User) query.uniqueResult();
    }

    @Override
    public List<User> getAll() {
        Query<User> query = getSession().createQuery("from User", User.class);
        return query.list();
    }

    @Override
    public void deleteAll() {
        Query query = getSession().createQuery("delete from User");
        query.executeUpdate();
    }

    @Override
    public void flush() {
        getSession().flush();
    }
}
