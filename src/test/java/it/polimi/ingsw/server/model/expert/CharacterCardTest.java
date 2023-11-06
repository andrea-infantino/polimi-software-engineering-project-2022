package it.polimi.ingsw.server.model.expert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {
    CharacterCard characterCard0, characterCard1, characterCard4, characterCard8;

    @BeforeEach
    void SetUp(){
        characterCard0 = new CharacterCard(0);
        characterCard1 = new CharacterCard(1);
        characterCard4 = new CharacterCard(4);
        characterCard8 = new CharacterCard(8);
    }

    @Test
    public void TestCharacterCard(){
        //Test getId
        assertEquals(0,characterCard0.getId());
        assertEquals(4,characterCard4.getId());
        assertEquals(8, characterCard8.getId());
        //Test getCost
        assertEquals(1, characterCard0.getCost());
        assertEquals(2, characterCard1.getCost());
        assertEquals(2, characterCard4.getCost());
        assertEquals(3, characterCard8.getCost());
        //Test increaseCost
        characterCard8.increaseCost();
        assertEquals(4, characterCard8.getCost());
        //Test attribute remainingEntryTiles
        assertEquals(-1, characterCard0.remainingNoEntryTiles);
        assertEquals(4, characterCard4.remainingNoEntryTiles);
        assertEquals(-1, characterCard8.remainingNoEntryTiles);
    }
}