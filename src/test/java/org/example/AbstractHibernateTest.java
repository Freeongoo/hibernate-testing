package org.example;

import org.example.persistance.HibernateUtil;
import org.example.persistance.SessionHolder;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractHibernateTest {
    protected static SessionFactory sessionFactory;
    protected Session session;

    @BeforeClass
    public static void classSetUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Before
    public void setUp() {
        beginSessionTransactionAndSaveToHolder();
        clearDB();
    }

    @After
    public void tearDown() {
        sessionCommitAndClose();
    }

    @AfterClass
    public static void classTearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // TODO: don't forget to add a call to delete methods when adding a new Entity
    private void clearDB() {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.deleteAll();
    }

    private void beginSessionTransactionAndSaveToHolder() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        SessionHolder.set(session);
    }

    protected void commitAndReopenSession() {
        sessionCommitAndClose();
        beginSessionTransactionAndSaveToHolder();
    }

    private void sessionCommitAndClose() {
        session.getTransaction().commit();
        session.close();
        SessionHolder.set(null);
    }
}
