package org.example.repository.impl;

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
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User forDeleteUser = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        int id = userRepository.createUser(forDeleteUser);
        forDeleteUser.setId(id);

        userRepository.deleteUser(forDeleteUser);

        commitAndReopenSession();

        User userFromDb = userRepository.getUser(id);

        assertThat(userFromDb, equalTo(null));
    }

    @Test(expected = NotExistUserException.class)
    public void deleteUser_WhenNotExistUserId() throws NotExistUserException {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User userCreated = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        userCreated.setId(1);
        userRepository.deleteUser(userCreated);
    }

    @Test
    public void updateUser() throws DuplicateUserException, NotExistUserException {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User userCreated = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        User userExpected = UserUtil.createUserWithoutId("second", "second", "second", "123");

        int id = userRepository.createUser(userCreated);
        userExpected.setId(id);

        commitAndReopenSession();

        userRepository.updateUser(userExpected);

        User actualUser = userRepository.getUser(id);
        assertThat(actualUser, equalTo(userExpected));
    }

    @Test
    public void createUser() throws DuplicateUserException {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User expectedUser = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        userRepository.createUser(expectedUser);

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
        userRepository.createUser(user);
    }

    @Test
    public void getUser() throws DuplicateUserException {
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User user = UserUtil.createUserWithoutId(userName, firstName, lastName, password);
        int id = userRepository.createUser(user);

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

        User userFromDb = userRepository.getUserByUserName(userName);

        assertThat(userFromDb, equalTo(expectedUser));
    }

    @Test
    public void getUserList() throws DuplicateUserException {
        User user1 = UserUtil.createUserWithoutId("first", "first", "first", "123");
        User user2 = UserUtil.createUserWithoutId("second", "second", "second", "123");

        int id1 = userRepository.createUser(user1);
        int id2 = userRepository.createUser(user2);

        user1.setId(id1);
        user2.setId(id2);

        ArrayList<User> users = new ArrayList<>();
        users.add(user2);
        users.add(user1);

        int expectedCount = 2;
        List<User> userList = userRepository.getUserList();

        assertThat(userList.size(), equalTo(expectedCount));
        assertThat(userList, containsInAnyOrder(users.toArray()));
    }
}