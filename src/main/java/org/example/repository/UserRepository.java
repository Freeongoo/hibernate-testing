package org.example.repository;

import org.example.entity.old_style_xml.User;

public interface UserRepository extends Repository<User, Integer> {

    User getByUserName(String userName);
}
