package org.example.repository.impl;

import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;
import org.example.repository.UserRepository;
import org.example.entity.User;
import org.example.persistance.SessionHolder;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public void deleteUser(User user) throws NotExistUserException {
        Session session = SessionHolder.get();
        User persistentInstance = getUser(user.getId());

        if (persistentInstance == null)
            throw new NotExistUserException("Try delete not exist user");

        session.delete(persistentInstance);
    }

    @Override
    public void updateUser(User user) {
        Session session = SessionHolder.get();
        session.update(user);
    }

    @Override
    public int createUser(User user) throws DuplicateUserException {
        if (getUserByUserName(user.getUserName()) != null)
            throw new DuplicateUserException("Duplicate User with userName: \"" + user.getUserName() + "\"");

        Session session = SessionHolder.get();
        return (int) session.save(user);
    }

    @Override
    public User getUser(int id) {
        Session session = SessionHolder.get();
        return session.get(User.class, id);
    }

    @Override
    public User getUserByUserName(String userName) {
        Session session = SessionHolder.get();
        Query query = session.createQuery("from User where userName = :userName");
        query.setParameter("userName", userName);
        return (User) query.uniqueResult();
    }

    @Override
    public List<User> getUserList() {
        Session session = SessionHolder.get();
        Query query = session.createQuery("from User");
        return query.list();
    }

    @Override
    public void deleteAll() {
        Session session = SessionHolder.get();
        Query query = session.createQuery("delete from User");

        query.executeUpdate();
    }
}
