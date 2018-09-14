package org.example.HQL.impl;

import org.example.AbstractHibernateTest;
import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;
import org.example.repository.UserRepository;
import org.example.entity.User;
import org.example.repository.hql.UserRepositoryHql;
import org.example.util.UserUtil;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class UserRepositoryHqlTest extends AbstractHibernateTest {
    private UserRepository userRepository;

    @Override
    public void setUp() {
        super.setUp();
        userRepository = new UserRepositoryHql();
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

        userRepository.deleteUser(id);
        User userFromDb = userRepository.getUser(id);

        assertThat(userFromDb, equalTo(null));
    }

    @Test(expected = NotExistUserException.class)
    public void deleteUser_WhenNotExistUserId() throws NotExistUserException {
        int notExistUserId = -1;
        userRepository.deleteUser(notExistUserId);
    }

    @Test
    // TODO: Cannot testing when create new user and try update it. Working when update existing user in DB
    public void updateUser() throws DuplicateUserException, NotExistUserException {
        int idAdmin = 1;
        String userName = "newUser";
        String firstName = "first";
        String lastName = "last";
        String password = "query";

        User userForUpdate = UserUtil.createUser(idAdmin, userName, firstName, lastName, password);
        userRepository.updateUser(idAdmin, userForUpdate);

        User expectedUser = userRepository.getUser(idAdmin);

        assertThat(expectedUser, equalTo(userForUpdate));
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
    public void getUser_WhenNotExist() throws DuplicateUserException {
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
    public void getUserList() {
        int expectedCount = 3;
        List<User> userList = userRepository.getUserList();

        assertThat(userList.size(), equalTo(expectedCount));
    }
}