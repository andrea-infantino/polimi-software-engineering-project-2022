package it.polimi.ingsw.server.model;

import java.util.*;

/**
 * class that represent a single round in a game
 */
public class GameRound {
    private List<Integer> order;
    private Map<Integer, AssistantCard> assistantCardsPlayed;
    public int roundCounter;
    public PlayerTurn currentTurn;

    /**
     * constructor
     * @param order a list representing players' order inside that turn
     */
    public GameRound (List<Integer> order){
        this.order = order;
        assistantCardsPlayed = new HashMap<>();
        roundCounter = 0;
    }

    /**
     * request for the players order in that turn
     * @return the players order in that turn
     */
    public List<Integer> getOrder(){
        return order;
    }

    /**
     * remove all the cards played in the previous round
     */
    public void resetCardsPlayed(){
        assistantCardsPlayed = new HashMap<>();
    }

    /**
     * set the order in the turn
     * @param order the order in the turn
     */
    public void setOrder(List<Integer> order){
        this.order = order;
    }

    /**
     * request for assistant cards already played by a player
     * @return the assistant cards already played by a player
     */
    public Map<Integer, AssistantCard> getAssistantCardsPlayed(){
        return assistantCardsPlayed;
    }

    /**
     * method that return every card that the player can play in this turn
     * @param player the player that is next to play
     * @return a list of integer representing every card that the player inserted as parameter can play in this turn
     */
    public List<Integer> playableCards(Player player){
        List<AssistantCard> canPlayCards = new ArrayList<>(player.getDeck());
        List<AssistantCard> toRemove = new ArrayList<>();
        for (AssistantCard ac : canPlayCards)
            for (AssistantCard a : assistantCardsPlayed.values())
                if (ac.getSpeedValue() == a.getSpeedValue())
                    toRemove.add(ac);
        canPlayCards.removeAll(toRemove);
        if (canPlayCards.isEmpty())
            canPlayCards = new ArrayList<>(player.getDeck());
        List <Integer> canPlayCardIds = new ArrayList<>();
        for(AssistantCard card : canPlayCards)
            canPlayCardIds.add(card.getSpeedValue());
        return canPlayCardIds;
    }

}
