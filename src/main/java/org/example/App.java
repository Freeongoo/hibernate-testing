package org.example;

import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;
import org.example.repository.hql.UserRepositoryHql;
import org.example.entity.User;
import org.example.persistance.HibernateUtil;
import org.example.persistance.SessionHolder;
import org.example.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        // example using...
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        SessionHolder.set(session);

        UserRepository userRepository = new UserRepositoryHql();
        User user = new User();
        user.setUserName("admin");
        user.setFirstName("a");
        user.setLastName("b");
        user.setPassword("password");
        try {
            userRepository.updateUser(1, user);
        } catch (DuplicateUserException | NotExistUserException e) {
            e.printStackTrace();
        } finally {
            //session.getTransaction().rollback();
            session.getTransaction().commit();
            session.close();
            sessionFactory.close();
        }
    }
}
