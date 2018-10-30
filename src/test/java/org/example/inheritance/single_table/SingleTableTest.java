package org.example.inheritance.single_table;

import org.example.AbstractHibernateTest;
import org.example.entity.inheritance.single_table.ProductSingleTable;
import org.example.entity.inheritance.single_table.UserSingleTable;
import org.junit.Test;

public class SingleTableTest extends AbstractHibernateTest {

    @Test
    public void test() {
        UserSingleTable userSingleTable = new UserSingleTable(123, "Mike");
        ProductSingleTable productSingleTable = new ProductSingleTable(321, "ProductName");

        session.save(userSingleTable);
        session.save(productSingleTable);

        flushAndClearSession();

        printStructureTable("base_single_table");
        showContentTable("base_single_table");
    }
}
