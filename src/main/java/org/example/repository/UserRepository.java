package org.example.repository;

import org.example.entity.User;
import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;

import java.util.List;

public interface UserRepository {

    void deleteUser(int id) throws NotExistUserException;

    void updateUser(int id, User user) throws DuplicateUserException, NotExistUserException;

    int createUser(User user) throws DuplicateUserException;

    User getUser(int id);

    User getUserByUserName(String userName);

    List<User> getUserList();
}
