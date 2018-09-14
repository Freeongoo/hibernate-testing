package org.example.persistance;

import org.hibernate.Session;

public class SessionHolder {
    private static final ThreadLocal<Session> threadLocalScope = new  ThreadLocal<>();

    public static Session get() {
        return threadLocalScope.get();
    }

    public static void set(Session session) {
        threadLocalScope.set(session);
    }
}
