package org.example.repository.hql;

import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;
import org.example.repository.UserRepository;
import org.example.entity.User;
import org.example.persistance.SessionHolder;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryHql implements UserRepository {
    @Override
    public void deleteUser(int id) throws NotExistUserException {
        Session session = SessionHolder.get();
        Query query = session.createQuery("delete User where id = :id");
        query.setParameter("id", id);

        int result = query.executeUpdate();
        if (result == 0) throw new NotExistUserException("Try delete not exist User by id: \"" + id + "\"");
    }

    @Override
    public void updateUser(int id, User user) throws DuplicateUserException, NotExistUserException {
        User userFromDb = getUserByUserName(user.getUserName());
        if (userFromDb != null && userFromDb.getId() != id)
            throw new DuplicateUserException("Duplicate User by userName: \"" + user.getUserName() + "\"");

        Session session = SessionHolder.get();
        Query query = session.createQuery("update User set userName = :userName, firstName = :firstName, lastName = :lastName, password = :password where id = :id");
        query.setParameter("userName", user.getUserName());
        query.setParameter("firstName", user.getFirstName());
        query.setParameter("lastName", user.getLastName());
        query.setParameter("password", user.getPassword());
        query.setParameter("id", id);

        int result = query.executeUpdate();
        if (result == 0) throw new NotExistUserException("Try update not exist User by id: \"" + id + "\"");
    }

    @Override
    // In HQL, only the INSERT INTO … SELECT … is supported; there is no INSERT INTO … VALUES
    public int createUser(User user) throws DuplicateUserException {
        if (getUserByUserName(user.getUserName()) != null)
            throw new DuplicateUserException("Duplicate User with userName: \"" + user.getUserName() + "\"");

        Session session = SessionHolder.get();
        int userId = (int) session.save(user);
        return userId;
    }

    @Override
    public User getUser(int id) {
        Session session = SessionHolder.get();
        Query query = session.createQuery("from User where id = :id");
        query.setParameter("id", id);
        return (User) query.uniqueResult();
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
}
