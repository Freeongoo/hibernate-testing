package org.example.entity.relations.OneToMany.JoinColumn;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "pupil")
public class Pupil {

    private Long id;
    private String name;
    private Integer age;

    private ClassRoom classRoom;

    public Pupil() {
    }

    public Pupil(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @ManyToOne
    @JoinColumn(name = "class_room_id", nullable = false)
    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pupil pupil = (Pupil) o;
        return Objects.equals(id, pupil.id) &&
                Objects.equals(name, pupil.name) &&
                Objects.equals(age, pupil.age);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, age);
    }
}
