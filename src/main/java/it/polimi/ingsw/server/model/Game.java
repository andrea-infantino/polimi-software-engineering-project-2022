package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.TowerColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * class that represent a single game
 */
public class Game {
    private int playerNumber;
    private Board board;
    private List<Player> players;
    private List<Team> teams;
    private boolean expertMode;
    public GameRound currentRound;
    public int winnerTeamId;

    /**
     * request for teams in the game
     * @return a list of team
     */
    public List<Team> getTeams(){
        return teams;
    }

    /**
     * request for the number of players in the game
     * @return the players in the game
     */
    public int getPlayerNumber(){
        return playerNumber;
    }

    /**
     * request for the game board
     * @return the game board
     */
    public Board getBoard(){
        return board;
    }

    /**
     * request for the players in the game
     * @return the players playing in the game
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * method that adds a new team in the game
     * @param newTeam the team to add
     */
    public void addTeam(Team newTeam){
        if (teams.size()<3)
            teams.add(newTeam);
    }

    /**
     * method that adds a player in the game
     * @param newPlayer the player to add
     */
    public void addPlayer(Player newPlayer){
        if (players.size()<playerNumber)
            players.add(newPlayer);

    }

    /**
     * constructor
     * @param expertMode a boolean representing whether the game is in expert mode or not
     * @param playerNumber the number of players playing in the game
     */
    public Game (boolean expertMode, int playerNumber){
        this.expertMode = expertMode;
        this.playerNumber = playerNumber;
        board = new Board(playerNumber);
        players = new ArrayList<>();
        teams = new ArrayList<>();
        winnerTeamId = -1;
    }

    /**
     * method that checks whether the game is in expert mode or not
     * @return true if the game is in expert mode, false otherwise
     */
    public boolean isExpertMode(){
        return expertMode;
    }

    /**
     * method that returns the player, given its id
     * @param id identifier of the player
     * @return the player that corresponds to that id
     */
    public Player getPlayerById(int id){
        for(Player p : players)
            if (id == p.getId())
                return p;
        return null;
    }

    /**
     * method that returns the team, given the towers' color
     * @param towerColor towers' color
     * @return the team that corresponds to that color
     */
    public Team getTeamByTowerColor (TowerColor towerColor){
        for (Team t : teams)
            if (towerColor == t.towerColor)
                return t;
        return null;
    }

    /**
     * method that returns the team, given the team's id
     * @param teamId id of the team
     * @return the team that corresponds to that id
     */
    public Team getTeamById (int teamId){
        for (Team t : teams)
            if (t.getId() == teamId)
                return t;
        return null;
    }

    /**
     * method that represents the fact that a player plays an assistant card
     * @param player the current player (who's playing)
     * @param assistantCard the card that is chosen by the player
     */
    public void playCard(Player player, AssistantCard assistantCard){
        currentRound.getAssistantCardsPlayed().put(player.getId(), assistantCard);
        getPlayerById(player.getId()).removeAssistantCard(assistantCard.getSpeedValue());
    }
}
