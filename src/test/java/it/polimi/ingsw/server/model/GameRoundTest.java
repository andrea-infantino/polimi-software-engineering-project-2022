package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameRoundTest {
    GameRound gameRound;
    List<Integer> order = new ArrayList<>();
    AssistantCard assistantCard;
    Player player0, player1;
    Map<Integer, AssistantCard> map;
    List<Integer> canPlayCards;

    @BeforeEach
    void SetUp(){
        order.add(0);
        order.add(1);
        gameRound = new GameRound(order);
        assistantCard = new AssistantCard(5);
        player0 = new Player(0, "Giuseppe");
        player1 = new Player(1, "Franco");
        map = new HashMap<>();
        canPlayCards = new ArrayList<>();
    }

    @Test
    public void testGameRound(){
        //Test setOrder
        gameRound.setOrder(order);
        assertEquals(order, gameRound.getOrder());
        //Test initial value of the attribute roundCounter
        assertEquals(0, gameRound.roundCounter);
        //Test getAssistantCardsPlayed and playCard
        assertTrue(gameRound.getAssistantCardsPlayed().isEmpty());
        //Test playableCards
        for (int i = 1; i < 11; i++){
              canPlayCards.add(i);
        }
        assertEquals(canPlayCards, gameRound.playableCards(player1));

    }
}