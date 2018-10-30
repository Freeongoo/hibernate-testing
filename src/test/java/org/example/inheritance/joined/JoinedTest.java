package org.example.inheritance.joined;

import org.example.AbstractHibernateTest;
import org.example.entity.inheritance.joined.ProductJoined;
import org.example.entity.inheritance.joined.UserJoined;
import org.example.entity.inheritance.single_table.ProductSingleTable;
import org.example.entity.inheritance.single_table.UserSingleTable;
import org.junit.Test;

public class JoinedTest extends AbstractHibernateTest {

    @Test
    public void test() {
        UserJoined userJoined = new UserJoined(123, "Mike");
        ProductJoined productJoined = new ProductJoined(321, "ProductName");

        session.save(userJoined);
        session.save(productJoined);

        flushAndClearSession();

        printStructureTable("base_joined");
        showContentTable("base_joined");

        printStructureTable("product_joined");
        showContentTable("product_joined");

        printStructureTable("user_joined");
        showContentTable("user_joined");
    }
}
