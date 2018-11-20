package org.example.entity.relations.OneToMany.JoinColumn;

import org.example.AbstractHibernateTest;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ClassRoomAndPupilTest extends AbstractHibernateTest {

    @Test
    public void save_WhenOnlyClassRoom() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");
        session.persist(classRoom);
        flushAndClearSession();

        List<ClassRoom> results = getAllListClassRoom();
        assertThat(results.size(), equalTo(1));

        printStructureTable("class_room");
        showContentTable("class_room");
    }

    // try save without relation with ClassRoom - but we set required
    // @JoinColumn(name = "class_room_id", nullable = false)
    @Test(expected = PersistenceException.class)
    public void save_WhenOnlyPupil() {
        Pupil pupil = new Pupil("John", 20);
        session.persist(pupil);
        flushAndClearSession();
    }

    // before set object "classRoom" must be save in db
    @Test
    public void save_WhenSavePupilWithClassRoom() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");
        session.persist(classRoom);
        flushAndClearSession();

        Pupil pupil = new Pupil("John", 20);
        pupil.setClassRoom(classRoom);
        session.persist(pupil);
        flushAndClearSession();

        printStructureTable("pupil");
        showContentTable("pupil");
    }

    @Test(expected = IllegalStateException.class)
    public void save_WhenSavePupilWithClassRoom_WhenClassRoomNotPersistence() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");

        Pupil pupil = new Pupil("John", 20);
        pupil.setClassRoom(classRoom);
        session.persist(pupil);
        flushAndClearSession();
    }

    @Test(expected = IllegalStateException.class)
    public void save_WhenSavePupilWithClassRoom_WhenClassRoomSavedButResetId() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");
        session.persist(classRoom);
        flushAndClearSession();

        classRoom.setId(null); // :)

        Pupil pupil = new Pupil("John", 20);
        pupil.setClassRoom(classRoom);
        session.persist(pupil);
        flushAndClearSession();

        printStructureTable("pupil");
        showContentTable("pupil");
    }

    // saved only ClassRoom - because not configure cascade
    // if need when save ClassRoom save Pupil automatically add: Cascade={CascadeType.ALL}
    @Test
    public void save_WhenClassRoomWithPupil() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");

        Pupil pupil1 = new Pupil("John", 20);
        Pupil pupil2 = new Pupil("Mike", 21);

        List<Pupil> pupils = new ArrayList<>();
        pupils.add(pupil1);
        pupils.add(pupil2);

        classRoom.setPupils(pupils);

        session.persist(classRoom);
        flushAndClearSession();

        List<ClassRoom> listClassRoom = getAllListClassRoom();
        assertThat(listClassRoom.size(), equalTo(1));

        List<Pupil> listPupil = getAllListPupil();
        assertThat(listPupil.size(), equalTo(0));

        printStructureTable("class_room");
        showContentTable("class_room");

        printStructureTable("pupil");
        showContentTable("pupil");
    }

    private List<ClassRoom> getAllListClassRoom() {
        return session
                .createQuery("SELECT c FROM ClassRoom c", ClassRoom.class)
                .getResultList();
    }

    private List<Pupil> getAllListPupil() {
        return session
                .createQuery("SELECT p FROM Pupil p", Pupil.class)
                .getResultList();
    }
}