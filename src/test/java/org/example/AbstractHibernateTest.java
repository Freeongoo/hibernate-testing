package org.example;

import org.example.persistance.HibernateUtil;
import org.example.persistance.SessionHolder;
import org.hibernate.FlushMode;
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
        session.setHibernateFlushMode(FlushMode.ALWAYS);
        SessionHolder.set(session);
    }

    @After
    public void tearDown() {
        SessionHolder.set(null);
        session.getTransaction().rollback();
        session.close();
    }

    @AfterClass
    public static void classTearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
