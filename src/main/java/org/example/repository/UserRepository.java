package org.example.repository;

import org.example.entity.User;
import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;

import java.util.List;

public interface UserRepository {

    void deleteUser(User user) throws NotExistUserException;

    void updateUser(User user) throws DuplicateUserException, NotExistUserException;

    int createUser(User user) throws DuplicateUserException;

    User getUser(int id);

    User getUserByUserName(String userName);

    List<User> getUserList();

    void deleteAll();
}
