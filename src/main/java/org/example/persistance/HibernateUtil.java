package org.example.persistance;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

	static public SessionFactory getSessionFactory() {
	    if (sessionFactory == null)
	        sessionFactory = initSessionFactory();
	    return sessionFactory;
	}

	static public void setSessionFactory(SessionFactory sessionFactory) {
	    HibernateUtil.sessionFactory = sessionFactory;
    }

	static private SessionFactory initSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
