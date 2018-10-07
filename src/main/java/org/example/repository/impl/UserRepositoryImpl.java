package org.example.repository.impl;

import org.example.entity.User;
import org.example.persistance.SessionHolder;
import org.example.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public void delete(User user) {
        Session session = getSession();
        User persistentInstance = get(user.getId());
        session.delete(persistentInstance);
    }

    @Override
    public void update(User user) {
        Session session = getSession();
        session.merge(user);
    }

    @Override
    public Integer create(User user) {
        Session session = getSession();
        return (Integer) session.save(user);
    }

    @Override
    public User get(Integer id) {
        Session session = getSession();
        return session.get(User.class, id);
    }

    @Override
    public User getByUserName(String userName) {
        Session session = getSession();
        Query query = session.createQuery("from User where userName = :userName");
        query.setParameter("userName", userName);
        return (User) query.uniqueResult();
    }

    @Override
    public List<User> getAll() {
        Session session = getSession();
        Query<User> query = session.createQuery("from User", User.class);
        return query.list();
    }

    @Override
    public void deleteAll() {
        Session session = getSession();
        Query query = session.createQuery("delete from User");

        query.executeUpdate();
    }

    @Override
    public void flush() {
        Session session = getSession();
        session.flush();
    }

    private Session getSession() {
        return SessionHolder.get();
    }
}
