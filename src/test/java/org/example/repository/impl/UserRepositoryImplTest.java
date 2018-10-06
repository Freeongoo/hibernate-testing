package org.example.repository.impl;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.AbstractHibernateTest;
import org.example.entity.User;
import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;
import org.example.repository.UserRepository;
import org.example.util.UserUtil;
import org.junit.Test;

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
    public void deleteUser() throws NotExistUserException, DuplicateUserException {
        User forDeleteUser = getUserByStaticValues();
        int id = userRepository.createUser(forDeleteUser);
        forDeleteUser.setId(id);

        userRepository.deleteUser(forDeleteUser);

        flushAndClearSession();

        User userFromDb = userRepository.getUser(id);
        assertThat(userFromDb, equalTo(null));
    }

    @Test(expected = NotExistUserException.class)
    public void deleteUser_WhenNotExistUserId() throws NotExistUserException {
        User user = getUserByStaticValues();

        flushAndClearSession();

        userRepository.deleteUser(user);
    }

    @Test
    public void updateUser() throws DuplicateUserException, NotExistUserException {
        User user = getUserByStaticValues();
        int idUserForUpdate = userRepository.createUser(user);

        flushAndClearSession();

        User expectedUser = UserUtil.createUserWithoutId("second", "second", "second", "123");
        expectedUser.setId(idUserForUpdate);
        userRepository.updateUser(expectedUser);

        flushAndClearSession();

        User actualUser = userRepository.getUser(idUserForUpdate);
        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test
    public void updateUser_OnlyOneField() throws DuplicateUserException, NotExistUserException {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User user = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        int idUserForUpdate = userRepository.createUser(user);

        flushAndClearSession();

        User expectedUser = UserUtil.createUserWithoutId(userName, "second", lastName, password);
        expectedUser.setId(idUserForUpdate);
        userRepository.updateUser(expectedUser);

        flushAndClearSession();

        User actualUser = userRepository.getUser(idUserForUpdate);
        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test(expected = DuplicateUserException.class)
    public void updateUser_WhenExistUserName() throws DuplicateUserException, NotExistUserException {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        String existUserName = "exist_user_name";

        User firstUser = UserUtil.createUserWithoutId(existUserName, "1", "1", "123");
        userRepository.createUser(firstUser);

        User user = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        int idUserForUpdate = userRepository.createUser(user);

        flushAndClearSession();

        User expectedUser = UserUtil.createUserWithoutId(existUserName, "second", "second", "123");
        expectedUser.setId(idUserForUpdate);
        userRepository.updateUser(expectedUser);

        flushAndClearSession();

        User actualUser = userRepository.getUser(idUserForUpdate);
        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test
    public void createUser() throws DuplicateUserException {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User expectedUser = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        userRepository.createUser(expectedUser);

        flushAndClearSession();

        User userFromDb = userRepository.getUserByUserName(userName);
        assertThat(userFromDb, equalTo(expectedUser));
    }

    @Test(expected = DuplicateUserException.class)
    public void createUser_WhenExistUserName() throws DuplicateUserException {
        String userNameExisted = "admin";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User user = UserUtil.createUserWithoutId(userNameExisted, firstName, lastName, password);

        userRepository.createUser(user);
        flushAndClearSession();

        userRepository.createUser(user);
    }

    @Test
    public void getUser() throws DuplicateUserException {
        User user = getUserByStaticValues();
        int id = userRepository.createUser(user);

        flushAndClearSession();

        User userFromDb = userRepository.getUser(id);
        assertThat(userFromDb, equalTo(user));
    }

    @Test
    public void getUser_WhenNotExist() {
        int NotExistUserId = -1;

        User userFromDb = userRepository.getUser(NotExistUserId);
        assertThat(userFromDb, equalTo(null));
    }

    @Test
    public void getUserByUserName() throws DuplicateUserException {
        String userName = "otherUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User expectedUser = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        userRepository.createUser(expectedUser);

        flushAndClearSession();

        User actualUser = userRepository.getUserByUserName(userName);
        assertThat(actualUser, equalTo(expectedUser));
    }

    @Test
    public void getUserByUserName_WhenRandom() throws DuplicateUserException {
        String userName = "MyUserName";

        User user = getUserByRandom();
        user.setUserName(userName);
        userRepository.createUser(user);

        flushAndClearSession();

        User userFromDb = userRepository.getUserByUserName(userName);
        assertThat(userFromDb, equalTo(user));
    }

    @Test
    public void getUserList() throws DuplicateUserException {
        User user1 = UserUtil.createUserWithoutId("first", "first", "first", "123");
        User user2 = UserUtil.createUserWithoutId("second", "second", "second", "123");

        int id1 = userRepository.createUser(user1);
        int id2 = userRepository.createUser(user2);

        flushAndClearSession();

        user1.setId(id1);
        user2.setId(id2);

        ArrayList<User> expectedList = new ArrayList<>();
        expectedList.add(user2);
        expectedList.add(user1);

        List<User> actualList = userRepository.getUserList();

        assertThat(actualList.size(), equalTo(expectedList.size()));
        assertThat(actualList, containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    public void deleteAll() throws DuplicateUserException {
        User user1 = UserUtil.createUserWithoutId("first", "first", "first", "123");
        User user2 = UserUtil.createUserWithoutId("second", "second", "second", "123");

        userRepository.createUser(user1);
        userRepository.createUser(user2);

        userRepository.deleteAll();

        flushAndClearSession();

        List<User> expectedList = new ArrayList<>();

        List<User> actualList = userRepository.getUserList();

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