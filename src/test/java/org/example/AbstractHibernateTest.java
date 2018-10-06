package org.example;

import org.example.persistance.HibernateUtil;
import org.example.persistance.SessionHolder;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.*;

public abstract class AbstractHibernateTest {
    protected static SessionFactory sessionFactory;
    protected Session session;

    @BeforeClass
    public static void classSetUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        SessionHolder.set(session);
    }

    @After
    public void tearDown() {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.deleteAll();

        SessionHolder.set(null);
        session.getTransaction().commit();
        session.close();
    }

    public void commitAndReopenSession() {
        session.flush();
        session.getTransaction().commit();
        session.close();
        SessionHolder.set(null);

        session = sessionFactory.openSession();
        session.beginTransaction();
        SessionHolder.set(session);
    }

    @AfterClass
    public static void classTearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
