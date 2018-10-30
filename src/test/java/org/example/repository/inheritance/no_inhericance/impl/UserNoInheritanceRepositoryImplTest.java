package org.example.repository.inheritance.no_inhericance.impl;

import org.example.AbstractHibernateTest;
import org.example.entity.inheritance.no_inheritance.UserNoInheritance;
import org.example.entity.old_style_xml.User;
import org.example.repository.inheritance.no_inhericance.UserNoInheritanceRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class UserNoInheritanceRepositoryImplTest extends AbstractHibernateTest {

    private UserNoInheritanceRepository repository;

    @Override
    public void setUp() {
        super.setUp();
        repository = new UserNoInheritanceRepositoryImpl();
    }

    @Test
    public void getAll() {
        UserNoInheritance userNoInheritance = new UserNoInheritance(123L, "John");
        UserNoInheritance userNoInheritance2 = new UserNoInheritance(321L, "Mike");

        repository.create(userNoInheritance);
        repository.create(userNoInheritance2);

        flushAndClearSession();

        ArrayList<UserNoInheritance> expectedList = new ArrayList<>();
        expectedList.add(userNoInheritance);
        expectedList.add(userNoInheritance2);

        List<UserNoInheritance> actualList = repository.getAll();

        assertThat(actualList.size(), equalTo(expectedList.size()));
        assertThat(actualList, containsInAnyOrder(expectedList.toArray()));
    }
}