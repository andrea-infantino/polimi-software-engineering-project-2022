package it.polimi.ingsw.server.model.expert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpertPlayerTest {
    ExpertPlayer expertPlayer;

    @BeforeEach
    void SetUp(){
        expertPlayer = new ExpertPlayer(2, "Franco");
    }

    @Test
    public void testExpertPlayer(){
        //Test getId
        assertEquals(2,expertPlayer.getId());
        //Test getName
        assertEquals("Franco", expertPlayer.getName());
        //Test getCoins
        assertEquals(1, expertPlayer.getCoins());
        //Test addCoins
        expertPlayer.addCoin();
        assertEquals(2, expertPlayer.getCoins());
        //Test removeCoins
        expertPlayer.removeCoins(1);
        assertEquals(1, expertPlayer.getCoins());
    }

}