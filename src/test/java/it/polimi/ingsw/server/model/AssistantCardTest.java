package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantCardTest {
    AssistantCard assistantCard1;
    AssistantCard assistantCard2;

    @BeforeEach
    void SetUp(){
        assistantCard1 = new AssistantCard(7);
        assistantCard2 = new AssistantCard(2);
    }

    @Test
    public void testAssistantCard(){
        //Test getMoveValue
        assertEquals(4, assistantCard1.getMoveValue());
        assertEquals(1, assistantCard2.getMoveValue());
        //Test getSpeedValue
        assertEquals(7, assistantCard1.getSpeedValue());
        assertEquals(2, assistantCard2.getSpeedValue());
    }
}