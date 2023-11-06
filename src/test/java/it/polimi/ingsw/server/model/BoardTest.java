package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;
    List<Color> studentsOnIslands, colorListed;
    Bag bag;

    @BeforeEach
    void SetUp(){
        board = new Board(2);
        studentsOnIslands = new ArrayList<>();
        colorListed = new ArrayList<>();
        for (int i = 0; i < 2 ; i++){
            colorListed.add(Color.BLUE);
            colorListed.add(Color.RED);
            colorListed.add(Color.YELLOW);
            colorListed.add(Color.GREEN);
            colorListed.add(Color.PINK);
        }

    }

    boolean hasSameElements(List<Color> list, List<Color> colorList){
        for (Color c : list){
            colorList.removeIf(c::equals);
        }
        return colorList.isEmpty();
    }

    @Test
    public void TestBoard(){
        //Test constructor
        assertEquals(2, board.getClouds().size());
        assertEquals(12, board.getIslandGroups().size());
        assertFalse(board.threeIslandGroups());
        for (Color c : Color.values())
            assertTrue(board.professors.get(c));
        assertTrue(board.getMotherNatureIsland().getStudents().isEmpty());
        if (board.getMotherNatureIsland().getId() < 6)
            assertTrue(board.getIslandGroupById(board.getMotherNatureIsland().getId() + 6).getStudents().isEmpty());
        else
            assertTrue(board.getIslandGroupById(board.getMotherNatureIsland().getId() - 6).getStudents().isEmpty());
        for (int i = 0; i < 12; i++){
            if (i != board.getMotherNatureIsland().getId() && i !=  board.getMotherNatureIsland().getId() + 6 && i != board.getMotherNatureIsland().getId() - 6) {
                assertEquals(1, board.getIslandGroupById(i).getStudents().size());
                studentsOnIslands.add(board.getIslandGroupById(i).getStudents().get(0));
            }
        }
        assertTrue(hasSameElements(studentsOnIslands, colorListed));
        //Test getBag
        bag = board.getBag();
        assertEquals(bag, board.getBag());
        //Test setMotherNatureIsland
        board.setMotherNatureIsland(3);
        assertEquals(3, board.getMotherNatureIsland().getId());
        //Test Join
        board.Join(10, 11);
        assertEquals(11, board.getIslandGroups().size());
        assertEquals(2, board.getIslandGroupById(10).islandsNumber);
        //Test refillCloud
        board.refillCloud(board.getCloudById(1));
        assertEquals(3, board.getCloudById(1).getStudents().size());
    }
}