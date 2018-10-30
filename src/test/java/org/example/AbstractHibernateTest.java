package org.example;

import org.example.persistance.HibernateUtil;
import org.example.persistance.SessionHolder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.List;

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

    protected void printStructureTable(String tableName) {
        Session session = SessionHolder.get();
        Query query1 = session.createNativeQuery("show columns from " + tableName);
        List<Object[]> results = query1.getResultList();
        System.out.println("\n===================");
        System.out.println("Structure table \"" + tableName + "\":");
        for (Object[] obj : results) {
            System.out.println("field: " + obj[0]);
            System.out.println("\ttype: " + obj[1]);
            System.out.println("\tnullable: " + obj[2]);
            System.out.println("\tkey: " + obj[3]);
            System.out.println("\tdefault: " + obj[4]);
        }
        System.out.println("===================\n");
    }

    protected void showContentTable(String tableName) {
        Session session = SessionHolder.get();
        Query query1 = session.createNativeQuery("select * from " + tableName);
        List<Object[]> results = query1.getResultList();
        System.out.println("\n===================");
        System.out.println("Content table \"" + tableName + "\":");
        int cnt = 0;
        for (Object[] obj : results) {
            cnt++;
            System.out.println("Row: " + cnt);
            for (Object objItem: obj) {
                System.out.println("\t" + objItem);
            }
        }
        System.out.println("===================\n");
    }
}
