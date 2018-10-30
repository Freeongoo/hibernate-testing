package org.example.inheritance.table_per_class;

import org.example.AbstractHibernateTest;
import org.example.entity.inheritance.table_per_class.ProductTablePerClass;
import org.example.entity.inheritance.table_per_class.UserTablePerClass;
import org.junit.Test;

public class TablePerClassTest extends AbstractHibernateTest {

    @Test
    public void test() {
        UserTablePerClass userTablePerClass = new UserTablePerClass(123, "Mike");
        ProductTablePerClass productTablePerClass = new ProductTablePerClass(321, "ProductName");

        session.save(userTablePerClass);
        session.save(productTablePerClass);

        flushAndClearSession();

        // table "base_table_per_class" - not exist

        printStructureTable("product_table_per_class");
        showContentTable("product_table_per_class");

        printStructureTable("user_table_per_class");
        showContentTable("user_table_per_class");
    }
}
