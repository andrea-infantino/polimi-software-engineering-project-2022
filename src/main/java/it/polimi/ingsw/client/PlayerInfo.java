package it.polimi.ingsw.client;

import it.polimi.ingsw.resources.enumerators.TowerColor;

import java.util.Map;

public class PlayerInfo {
    private int playerId, teamId, playerNumber;
    private boolean expertMode;
    private Map<Integer, String> playerNames;
    private Map<Integer, TowerColor> teams;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public boolean isExpertMode() {
        return expertMode;
    }

    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    public void setPlayerNames(Map<Integer, String> playerNames) {
        this.playerNames = playerNames;
    }

    public Map<Integer, String> getPlayerNames() {
        return playerNames;
    }

    public Map<Integer, TowerColor> getTeams() {
        return teams;
    }

    public void setTeams(Map<Integer, TowerColor> teams) {
        this.teams = teams;
    }
}
