package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandGroupTest {
    IslandGroup islandGroup;
    List<Color> students;

    @BeforeEach
    void SetUp(){
        islandGroup = new IslandGroup(2, 3);
        students = new ArrayList<>();
        students.add(Color.BLUE);
        students.add(Color.RED);
    }

    @Test
    public void TestIslandGroup(){
        //Test constructor
        assertEquals(1, islandGroup.islandsNumber);
        assertEquals(2, islandGroup.getId());
        assertFalse(islandGroup.hasMotherNature);
        assertFalse(islandGroup.hasNoEntryTile);
        assertNull(islandGroup.controlledBy);
        //Test getMaxNumber
        assertEquals(120, islandGroup.getMaxNumber());
        //Test setMaxNumber
        islandGroup.setMaxNumber(30);
        assertEquals(30, islandGroup.getMaxNumber());
        //Test setId
        islandGroup.setId(3);
        assertEquals(3, islandGroup.getId());
        //Test setInfluence
        islandGroup.setInfluence(TowerColor.BLACK, 4);
        assertEquals(4, islandGroup.getInfluence().get(TowerColor.BLACK));
        //Test addStudent
        islandGroup.addStudent(Color.GREEN);
        assertEquals(1, islandGroup.getStudentsByColor(Color.GREEN));
        //Test removeStudent
        islandGroup.removeStudent(Color.GREEN);
        assertTrue(islandGroup.getStudents().isEmpty());
        //Test addStudents
        islandGroup.addStudents(students);
        assertEquals(students, islandGroup.getStudents());
        //Test removeStudents
        islandGroup.removeStudents(students);
        assertTrue(islandGroup.getStudents().isEmpty());
    }
}