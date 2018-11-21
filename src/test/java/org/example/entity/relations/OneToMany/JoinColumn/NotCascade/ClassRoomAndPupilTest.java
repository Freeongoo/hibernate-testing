package org.example.entity.relations.OneToMany.JoinColumn.NotCascade;

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
    public void save_WhenClassRoomWithPupil_WhenSaveOnlyClassRoom() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");

        Pupil pupil1 = new Pupil("John", 20);
        pupil1.setClassRoom(classRoom);
        Pupil pupil2 = new Pupil("Mike", 21);
        pupil2.setClassRoom(classRoom);

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

    @Test
    public void save_WhenClassRoomWithPupil_WhenSaveAllEntity() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");

        Pupil pupil1 = new Pupil("John", 20);
        pupil1.setClassRoom(classRoom);
        Pupil pupil2 = new Pupil("Mike", 21);
        pupil2.setClassRoom(classRoom);

        List<Pupil> pupils = new ArrayList<>();
        pupils.add(pupil1);
        pupils.add(pupil2);

        classRoom.setPupils(pupils);

        session.persist(classRoom);
        session.persist(pupil1);
        session.persist(pupil2);
        flushAndClearSession();

        List<ClassRoom> listClassRoom = getAllListClassRoom();
        assertThat(listClassRoom.size(), equalTo(1));

        List<Pupil> listPupil = getAllListPupil();
        assertThat(listPupil.size(), equalTo(2));

        printStructureTable("class_room");
        showContentTable("class_room");

        printStructureTable("pupil");
        showContentTable("pupil");
    }

    // cannot delete classRoom - not set cascade add:
    // @OnDelete(action = OnDeleteAction.CASCADE)
    @Test(expected = javax.persistence.PersistenceException.class)
    public void save_WhenClassRoomWithPupil_WhenSaveAllEntity_WhenDeleteParent() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");

        Pupil pupil1 = new Pupil("John", 20);
        pupil1.setClassRoom(classRoom);
        Pupil pupil2 = new Pupil("Mike", 21);
        pupil2.setClassRoom(classRoom);

        List<Pupil> pupils = new ArrayList<>();
        pupils.add(pupil1);
        pupils.add(pupil2);

        classRoom.setPupils(pupils);

        session.persist(classRoom);
        session.persist(pupil1);
        session.persist(pupil2);
        flushAndClearSession();

        session.delete(classRoom);
        flushAndClearSession();
    }

    // cannot delete classRoom - not set cascade
    @Test
    public void save_WhenClassRoomWithPupil_WhenSaveAllEntity_WhenDeleteOnePupil() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");

        Pupil pupil1 = new Pupil("John", 20);
        pupil1.setClassRoom(classRoom);
        Pupil pupil2 = new Pupil("Mike", 21);
        pupil2.setClassRoom(classRoom);

        List<Pupil> pupils = new ArrayList<>();
        pupils.add(pupil1);
        pupils.add(pupil2);

        classRoom.setPupils(pupils);

        session.persist(classRoom);
        session.persist(pupil1);
        session.persist(pupil2);
        flushAndClearSession();

        classRoom.getPupils().remove(0);
        flushAndClearSession();

        // yes - delete from collection :)
        List<Pupil> pupilsAfterDelete = classRoom.getPupils();
        assertThat(pupilsAfterDelete.size(), equalTo(1));

        // check count pupil from DB
        // not change - because not set cascade. add if want to change count pupil:
        // @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL)
        List<Pupil> allListPupilFromDb = getAllListPupil();
        assertThat(allListPupilFromDb.size(), equalTo(2));

        printStructureTable("class_room");
        showContentTable("class_room");

        printStructureTable("pupil");
        showContentTable("pupil");
    }

    @Test
    public void save_WhenGetClassRoom() {
        ClassRoom classRoom = new ClassRoom(12, "MyClassRoom");

        Pupil pupil1 = new Pupil("John", 20);
        pupil1.setClassRoom(classRoom);
        Pupil pupil2 = new Pupil("Mike", 21);
        pupil2.setClassRoom(classRoom);

        List<Pupil> pupils = new ArrayList<>();
        pupils.add(pupil1);
        pupils.add(pupil2);

        classRoom.setPupils(pupils);

        session.persist(classRoom);
        session.persist(pupil1);
        session.persist(pupil2);
        flushAndClearSession();

        // when get ClassRoom - select only from ClassRoom - Lazy load :)
        ClassRoom classRoomFromDb = session.get(ClassRoom.class, 1L);
        assertThat(classRoomFromDb, equalTo(classRoom));

        // only when we try get list pupils = run select from pupil
        List<Pupil> pupilListFromDb = classRoomFromDb.getPupils();
        assertThat(pupilListFromDb.size(), equalTo(2));

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