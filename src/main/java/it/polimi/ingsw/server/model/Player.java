package it.polimi.ingsw.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent the single player
 */
public class Player {
    private String name;
    private int id;
    private List<AssistantCard> deck;
    private School school;

    /**
     * Check if the deck of a particular player is empty
     * @return true if the deck is empty
     */
    public boolean emptyDeck(){
        return deck.isEmpty();
    }

    /**
     * Class Player constructor
     * @param id player Id
     * @param name player nickname
     */
    public Player(int id, String name){
        this.id = id;
        this.name = name;
        deck = new ArrayList<>();
        for (int i = 1; i < 11; i++)
            deck.add(new AssistantCard(i));
        school = new School(id);
    }

    /**
     * Request for the player name (nickname)
     * @return player nickname
     */
    public String getName(){
        return name;
    }

    /**
     * Request for the player Id
     * @return player Id
     */
    public int getId(){
        return id;
    }

    /**
     * Request for the assistant cards list
     * @return deck made of assistant cards
     */
    public List<AssistantCard> getDeck(){
        return deck;
    }

    /**
     * Request for a particular assistant card by its speed value
     * @param speedValue related to a particular assistant card
     * @return Assistant card corresponding to that speed value
     */
    public AssistantCard getAssistantCardBySpeedValue(int speedValue){
        for(AssistantCard ac : deck)
            if (ac.getSpeedValue() == speedValue)
                return ac;
        return null;
    }

    /**
     * Request for remove an assistant card
     * @param speedValue the value on the top of the assistant card
     */
    public void removeAssistantCard(int speedValue) {
        deck.removeIf(a -> a.getSpeedValue() == speedValue);
    }

    /**
     * Request for the current player school
     * @return current player school
     */
    public School getSchool(){
        return school;
    }
}
