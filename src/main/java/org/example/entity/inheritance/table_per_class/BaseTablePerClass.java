package org.example.entity.inheritance.table_per_class;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "base_table_per_class")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseTablePerClass {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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

    public BaseTablePerClass() {
    }

    public BaseTablePerClass(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTablePerClass that = (BaseTablePerClass) o;
        return version == that.version &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, version);
    }
}
