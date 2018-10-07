package org.example.service.impl;

import org.example.entity.User;
import org.example.exception.DuplicateUserException;
import org.example.exception.NotExistUserException;
import org.example.repository.UserRepository;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(User user) throws NotExistUserException {
        if (get(user.getId()) == null)
            throw new NotExistUserException("Try delete not exist user");

        repository.delete(user);
    }

    @Override
    public void update(User user) throws DuplicateUserException, NotExistUserException {
        User userFromDb = get(user.getId());

        if (userFromDb == null)
            throw new NotExistUserException("Try update not exist user");

        User userFromUserName = getByUserName(user.getUserName());

        if (userFromUserName != null && userFromUserName.getId() != user.getId())
            throw new DuplicateUserException("Duplicate User by userName: \"" + user.getUserName() + "\"");

        repository.update(user);
    }

    @Override
    public Integer create(User user) throws DuplicateUserException {
        if (getByUserName(user.getUserName()) != null)
            throw new DuplicateUserException("Duplicate User with userName: \"" + user.getUserName() + "\"");

        return repository.create(user);
    }

    @Override
    public User get(Integer id) {
        return repository.get(id);
    }

    @Override
    public User getByUserName(String userName) {
        return repository.getByUserName(userName);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
