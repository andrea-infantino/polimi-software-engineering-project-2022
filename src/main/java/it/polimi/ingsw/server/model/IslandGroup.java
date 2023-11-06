package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.TowerColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class that represent a single group of islands
 */
public class IslandGroup extends StudentPosition{
    private int id;
    private Map<TowerColor, Integer> influence;
    public int islandsNumber;
    public boolean hasMotherNature;
    public boolean hasNoEntryTile;
    public TowerColor controlledBy;

    /**
     * constructor
     * @param id identifier of the island group (initially composed by only 1 island)
     * @param playerNumber number of players playing the game
     */
    public IslandGroup(int id, int playerNumber) {
        this.id = id;
        islandsNumber = 1;
        hasMotherNature = false;
        hasNoEntryTile = false;
        controlledBy = null;
        super.init();
        super.setMaxNumber(120);
        influence = new HashMap<>();
        influence.put(TowerColor.WHITE, 0);
        influence.put(TowerColor.BLACK, 0);
        if(playerNumber == 3)
            influence.put(TowerColor.GREY, 0);
    }

    /**
     * request for the id of the island group
     * @return the id of the island group
     */
    public int getId() {
        return id;
    }

    /**
     * set the id of the island group
     * @param id of the island group
     */
    public void setId(int id) { this.id = id;}

    /**
     * request for the influence value mapped with the player
     * @return the influence value mapped with the player
     */
    public Map<TowerColor, Integer> getInfluence () {
        return influence;
    }

    /**
     * set the influence of a player
     * @param color identifies the player (team)
     * @param influence the influence of the player (team)
     */
    public void setInfluence (TowerColor color, int influence) {
        this.influence.put(color, influence);
    }

    /**
     * method that returns the number of students on the island, given their color
     * @param c color of the student
     * @return the number of students of a certain color on the island
     */
    public int getStudentsByColor(Color c) {
        int n = 0;
        for (Color s : super.getStudents())
            if(s.equals(c))
                n++;
        return n;
    }
}
