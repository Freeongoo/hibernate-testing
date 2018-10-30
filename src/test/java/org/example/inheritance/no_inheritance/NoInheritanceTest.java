package org.example.inheritance.no_inheritance;

import org.example.AbstractHibernateTest;
import org.example.entity.inheritance.no_inheritance.ProductNoInheritance;
import org.example.entity.inheritance.no_inheritance.UserNoInheritance;
import org.junit.Test;

public class NoInheritanceTest extends AbstractHibernateTest {
    @Test
    public void test() {
        UserNoInheritance userNoInheritance = new UserNoInheritance(123, "Mike");
        ProductNoInheritance productNoInheritance = new ProductNoInheritance(321, "ProductName");

        session.save(userNoInheritance);
        session.save(productNoInheritance);

        flushAndClearSession();

        printStructureTable("user_no_inheritance");
        showContentTable("user_no_inheritance");

        printStructureTable("product_no_inheritance");
        showContentTable("product_no_inheritance");
    }
}
