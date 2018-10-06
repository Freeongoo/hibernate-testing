package org.example.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.entity.User;

public class UserUtil {
    public static User createUserWithoutId(String userName, String firstName, String lastName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(DigestUtils.md5Hex(password));

        return user;
    }
}
