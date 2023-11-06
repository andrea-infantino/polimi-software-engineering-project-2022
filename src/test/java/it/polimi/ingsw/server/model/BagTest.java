package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.resources.enumerators.Color.*;
import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    Bag bag;

    @BeforeEach
    void SetUp(){
        bag = new Bag();
    }

    boolean isColor(Color color){
        if (color == BLUE || color == PINK || color == GREEN || color == RED || color == YELLOW)
            return true;
        return false;
    }

    boolean isListOfColor(List<Color> colorList){
        boolean is = false;
        for (Color c : colorList){
            if (c == BLUE || c == PINK || c == GREEN || c == RED || c == YELLOW)
                is = true;
        }
        return is;
    }

    @Test
    public void testBag(){
        //Test extract
        assertTrue(isColor(bag.extract()));
        assertTrue(isListOfColor(bag.extract(109)));
        //Test isEmpty
        assertTrue(bag.isEmpty());
    }

}