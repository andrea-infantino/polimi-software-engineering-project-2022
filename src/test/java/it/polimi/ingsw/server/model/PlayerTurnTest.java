package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTurnTest {
    PlayerTurn playerTurn;

    @BeforeEach
    void SetUp(){
        playerTurn = new PlayerTurn(1);
    }

    @Test
    public void testPlayerTurn(){
        //Test getPlayerId
        assertEquals(1, playerTurn.getPlayerId());
        //Test initial values for the attributes
        assertFalse(playerTurn.card2Played);
        assertFalse(playerTurn.card4Played);
        assertFalse(playerTurn.card6Played);
        assertFalse(playerTurn.card8Played);
        assertNull(playerTurn.card9Played);
    }
}