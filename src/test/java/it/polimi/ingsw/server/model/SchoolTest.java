package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {
    School schoolTest;
    @BeforeEach
    void setUp(){
        schoolTest = new School(2);
    }
    @Test
    public void TestGetPlayerId(){
        assertEquals(2, schoolTest.getPlayerId());
    }
    @Test
    public void TestGetStudentsInDiningRoom(){
        schoolTest.addStudentInDiningRoom(Color.GREEN);
        schoolTest.addStudentInDiningRoom(Color.RED);
        schoolTest.addStudentInDiningRoom(Color.RED);
        assertEquals(1, schoolTest.getStudentsInDiningRoom(Color.GREEN));
        assertEquals(2, schoolTest.getStudentsInDiningRoom(Color.RED));
    }
    @Test
    public void TestGetDiningRoom() {
        schoolTest.addStudentInDiningRoom(Color.BLUE);
        schoolTest.addStudentInDiningRoom(Color.PINK);
        schoolTest.addStudentInDiningRoom(Color.YELLOW);
        schoolTest.addStudentInDiningRoom(Color.BLUE);
        boolean check = false;
        if (schoolTest.getStudentsInDiningRoom(Color.BLUE) == 2 && schoolTest.getStudentsInDiningRoom(Color.PINK) == 1 && schoolTest.getStudentsInDiningRoom(Color.YELLOW) == 1) {
            check = true;
        }
        assertTrue(check);
    }
    @Test
    public void TestRemoveStudents() {
        schoolTest.addStudentInDiningRoom(Color.BLUE);
        schoolTest.addStudentInDiningRoom(Color.YELLOW);
        schoolTest.addStudentInDiningRoom(Color.BLUE);
        List<Color> toRemove = new ArrayList<>();
        toRemove.add(Color.BLUE);
        boolean checkRemove = false;
        schoolTest.removeStudentsInDiningRoom(toRemove);
        if(schoolTest.getStudentsInDiningRoom(Color.BLUE) == 1 && schoolTest.getStudentsInDiningRoom(Color.YELLOW) == 1) {
            checkRemove = true;
        }
        assertTrue(checkRemove);

    }




}