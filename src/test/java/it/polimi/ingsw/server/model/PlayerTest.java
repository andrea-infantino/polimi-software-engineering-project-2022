package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player playerTest;
    List<AssistantCard>deckTest;

    @BeforeEach
    void setUp(){
        playerTest = new Player(11, "Andonio");
        deckTest = new ArrayList<AssistantCard>();

    }

    @Test
    public void testEmptyDeck_Constructor_RemoveAssistantCard(){
        for (int i = 1; i < 11; i++)
            playerTest.removeAssistantCard(i);
        assertTrue(playerTest.emptyDeck());
    }

    @Test
    public void testGetName_GetId(){
        assertEquals("Andonio", playerTest.getName());
        assertEquals(11, playerTest.getId());
    }

    @Test
    public void testGetDeck(){
        boolean check = true;
        for (int i = 1; i < 11; i++) {
            boolean found = false;
            for (AssistantCard a : playerTest.getDeck())
                if (i == a.getSpeedValue())
                    found = true;
            if(!found)
                check = false;
        }
        assertTrue(check);
    }

    @Test
    public void testGetAssistantCardBySpeedValue(){
        int i = 4;
        boolean checkSpeedValue = false;
        for (AssistantCard a : playerTest.getDeck()) {
            if (i == a.getSpeedValue())
                checkSpeedValue = true;
        }
        assertTrue(checkSpeedValue);

    }


}