package it.polimi.ingsw.server.model.expert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ExpertGameTest {
    ExpertGame expertGame;
    ExpertPlayer expertPlayer;

    @BeforeEach
    void SetUp(){
        expertGame = new ExpertGame(true, 2);
        expertPlayer = new ExpertPlayer(1, "Franco");
    }

    @Test
    public void testExpertGame(){
        //Test constructor and getCharacterCards
        assertEquals(18, expertGame.leftCoins);
        assertEquals(3, expertGame.getCharacterCards().length);
        assertTrue(expertGame.isExpertMode());
        assertEquals(3 , expertGame.getCharacterCards().length);
        assertNotEquals(expertGame.getCharacterCards()[0], expertGame.getCharacterCards()[1]);
        assertNotEquals(expertGame.getCharacterCards()[0], expertGame.getCharacterCards()[2]);
        assertNotEquals(expertGame.getCharacterCards()[1], expertGame.getCharacterCards()[2]);
        //Test playCharacterCard
        expertGame.playCharacterCard(expertPlayer, 0);
        assertEquals(1, expertPlayer.getCoins());
        assertEquals(18, expertGame.leftCoins);
    }
}