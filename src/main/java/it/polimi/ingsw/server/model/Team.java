package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.TowerColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent a single team
 */
public class Team {
    private List<Integer> players;
    private int id;
    public TowerColor towerColor;
    public int towers;

    /**
     * Class Team first constructor (2 or 3 players mode)
     * @param playerId player Id of a player who is part of that team
     * @param id that is the team identifier
     * @param playerNumber player numbers is playing in that game
     */
    public Team(int playerId, int id, int playerNumber){
        this.id = id;
        players = new ArrayList<>();
        towerColor = TowerColor.values()[id];
        players.add(playerId);
        if (playerNumber == 2 || playerNumber == 4)
            this.towers = 8;
        else
            this.towers = 6;
    }

    /**
     * Class Team second constructor (4 players mode)
     * @param id that is the team identifier
     */
    public Team(int id){
        this.id = id;
        players = new ArrayList<>();
        towerColor = TowerColor.values()[id];
        this.towers = 8;
    }

    /**
     * Request for add a palyer in that team
     * @param playerId of the new player to add
     */
    public void addPlayer(int playerId){
        players.add(playerId);
    }

    /**
     * Request for the list of the players in that team
     * @return players of that team
     */
    public List<Integer> getPlayers(){
        return players;
    }

    /**
     * Request for the team id
     * @return team id
     */
    public int getId(){
        return id;
    }

    /**
     * Check if all towers are placed
     * @return true if all towers of that team have been placed
     */
    public boolean allTowersPlaced(){
        return this.towers == 0;
    }
}
