package org.example.service;

import org.example.entity.User;
import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;

import java.util.List;

public interface UserService {

    void delete(User user) throws NotExistUserException;

    void update(User user) throws DuplicateUserException, NotExistUserException;

    Integer create(User user) throws DuplicateUserException;

    User get(Integer id);

    User getByUserName(String userName);

    List<User> getAll();

    public void deleteAll();
}
