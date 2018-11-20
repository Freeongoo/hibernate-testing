package org.example.entity.relations.OneToMany.JoinColumn;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "class_room")
public class ClassRoom {

    private Long id;
    private Integer number;
    private String title;

    private List<Pupil> pupils = Collections.emptyList();

    public ClassRoom() {
    }

    public ClassRoom(Integer number, String title) {
        this.number = number;
        this.title = title;
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
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(mappedBy = "classRoom")
    public List<Pupil> getPupils() {
        return pupils;
    }

    public void setPupils(List<Pupil> pupils) {
        this.pupils = pupils;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return Objects.equals(id, classRoom.id) &&
                Objects.equals(number, classRoom.number) &&
                Objects.equals(title, classRoom.title) &&
                Objects.equals(pupils, classRoom.pupils);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number, title, pupils);
    }
}
