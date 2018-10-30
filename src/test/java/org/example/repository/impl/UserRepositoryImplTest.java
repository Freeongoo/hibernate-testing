package org.example.repository.impl;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.AbstractHibernateTest;
import org.example.entity.old_style_xml.User;
import org.example.repository.UserRepository;
import org.example.util.UserUtil;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class UserRepositoryImplTest extends AbstractHibernateTest {
    private UserRepository userRepository;

    @Override
    public void setUp() {
        super.setUp();
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void deleteUser() {
        User forDeleteUser = getUserByStaticValues();
        int id = userRepository.create(forDeleteUser);

        flushAndClearSession();

        userRepository.delete(forDeleteUser);

        flushAndClearSession();

        User userFromDb = userRepository.get(id);
        assertThat(userFromDb, equalTo(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteUser_WhenNotExistUserId() {
        User user = getUserByStaticValues();

        flushAndClearSession();

        userRepository.delete(user);
    }

    @Test
    public void updateUser() {
        User user = getUserByStaticValues();
        int idUserForUpdate = userRepository.create(user);

        flushAndClearSession();

        User expectedUser = UserUtil.createUserWithoutId("second", "second", "second", "123");
        expectedUser.setId(idUserForUpdate);
        userRepository.update(expectedUser);

        flushAndClearSession();

        User actualUser = userRepository.get(idUserForUpdate);
        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test
    public void updateUser_OnlyOneField() {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User user = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        int idUserForUpdate = userRepository.create(user);

        flushAndClearSession();

        User expectedUser = UserUtil.createUserWithoutId(userName, "second", lastName, password);
        expectedUser.setId(idUserForUpdate);
        userRepository.update(expectedUser);

        flushAndClearSession();

        User actualUser = userRepository.get(idUserForUpdate);
        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test(expected = PersistenceException.class)
    public void updateUser_WhenExistUserName() {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        String existUserName = "exist_user_name";

        User firstUser = UserUtil.createUserWithoutId(existUserName, "1", "1", "123");
        userRepository.create(firstUser);

        User user = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        int idUserForUpdate = userRepository.create(user);

        flushAndClearSession();

        User expectedUser = UserUtil.createUserWithoutId(existUserName, "second", "second", "123");
        expectedUser.setId(idUserForUpdate);
        userRepository.update(expectedUser);

        flushAndClearSession();

        User actualUser = userRepository.get(idUserForUpdate);
        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test
    public void createUser() {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User expectedUser = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        userRepository.create(expectedUser);

        flushAndClearSession();

        User userFromDb = userRepository.getByUserName(userName);
        assertThat(userFromDb, equalTo(expectedUser));
    }

    @Test(expected = PersistenceException.class)
    public void createUser_WhenExistUserName() {
        String userNameExisted = "admin";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User user = UserUtil.createUserWithoutId(userNameExisted, firstName, lastName, password);

        userRepository.create(user);
        flushAndClearSession();

        userRepository.create(user);
        flushAndClearSession();
    }

    @Test
    public void getUser() {
        User user = getUserByStaticValues();
        int id = userRepository.create(user);

        flushAndClearSession();

        User userFromDb = userRepository.get(id);
        assertThat(userFromDb, equalTo(user));
    }

    @Test
    public void getUser_WhenNotExist() {
        int NotExistUserId = -1;

        User userFromDb = userRepository.get(NotExistUserId);
        assertThat(userFromDb, equalTo(null));
    }

    @Test
    public void getUserByUserName() {
        String userName = "otherUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User expectedUser = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        userRepository.create(expectedUser);

        flushAndClearSession();

        User actualUser = userRepository.getByUserName(userName);
        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test
    public void getUserByUserName_WhenRandom() {
        String userName = "MyUserName";

        User user = getUserByRandom();
        user.setUserName(userName);
        userRepository.create(user);

        flushAndClearSession();

        User userFromDb = userRepository.getByUserName(userName);
        assertThat(userFromDb, equalTo(user));
    }

    @Test
    public void getUserList() {
        User user1 = UserUtil.createUserWithoutId("first", "first", "first", "123");
        User user2 = UserUtil.createUserWithoutId("second", "second", "second", "123");

        userRepository.create(user1);
        userRepository.create(user2);

        flushAndClearSession();

        ArrayList<User> expectedList = new ArrayList<>();
        expectedList.add(user2);
        expectedList.add(user1);

        List<User> actualList = userRepository.getAll();

        assertThat(actualList.size(), equalTo(expectedList.size()));
        assertThat(actualList, containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    public void deleteAll() {
        User user1 = UserUtil.createUserWithoutId("first", "first", "first", "123");
        User user2 = UserUtil.createUserWithoutId("second", "second", "second", "123");

        userRepository.create(user1);
        userRepository.create(user2);

        flushAndClearSession();

        userRepository.deleteAll();

        flushAndClearSession();

        List<User> expectedList = new ArrayList<>();
        List<User> actualList = userRepository.getAll();

        assertThat(actualList.size(), equalTo(expectedList.size()));
        assertThat(actualList, containsInAnyOrder(expectedList.toArray()));
    }

    private User getUserByStaticValues() {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        return UserUtil.createUserWithoutId(userName, firstName, lastName, password);
    }

    private User getUserByRandom() {
        Faker faker = new Faker();

        String userName = faker.name().username();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String password = RandomStringUtils.random(10, true, true);

        return UserUtil.createUserWithoutId(userName, firstName, lastName, password);
    }
}