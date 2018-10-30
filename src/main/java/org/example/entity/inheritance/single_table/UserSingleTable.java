package org.example.entity.inheritance.single_table;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_single_table")
public class UserSingleTable extends BaseSingleTable {
    private String username;

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserSingleTable() {

    }

    public UserSingleTable(long version, String username) {
        super(version);
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSingleTable that = (UserSingleTable) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username);
    }
}
