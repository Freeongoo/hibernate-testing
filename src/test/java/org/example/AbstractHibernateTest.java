package org.example;

import org.example.persistance.HibernateUtil;
import org.example.persistance.SessionHolder;
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

    private void beginSessionTransactionAndSaveToHolder() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        SessionHolder.set(session);
    }

    private void sessionCommitAndClose() {
        session.getTransaction().rollback();
        session.close();
        SessionHolder.set(null);
    }

    protected void flushAndClearSession() {
        session.flush();
        session.clear();
    }
}
