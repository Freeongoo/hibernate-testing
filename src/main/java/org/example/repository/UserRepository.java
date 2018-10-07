package org.example.repository;

import org.example.entity.User;

public interface UserRepository extends Repository<User, Integer> {

    User getByUserName(String userName);

}
