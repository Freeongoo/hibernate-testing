package org.example.entity.inheritance.joined;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "base_joined")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseJoined {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected Integer id;
    protected long version;

    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public BaseJoined() {
    }

    public BaseJoined(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseJoined that = (BaseJoined) o;
        return version == that.version &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, version);
    }
}
