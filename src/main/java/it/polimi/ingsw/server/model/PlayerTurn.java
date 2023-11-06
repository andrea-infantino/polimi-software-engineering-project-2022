package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;

/**
 * Class that represent a player turn
 */
public class PlayerTurn {
    private int playerId;
    public boolean card2Played;
    public boolean card4Played;
    public boolean card6Played;
    public boolean card8Played;
    public Color card9Played;

    public boolean hasAlreadyPlayedACharacterCard;

    /**
     * Class PlayerTurn constructor
     * @param playerId that is playing the current turn
     */
    public PlayerTurn(int playerId){
        this.playerId = playerId;
        card2Played = false;
        card4Played = false;
        card6Played = false;
        card8Played = false;
        card9Played = null;
        hasAlreadyPlayedACharacterCard = false;
    }

    /**
     * Request for the current player Id
     * @return current player Id
     */
    public int getPlayerId(){
        return playerId;
    }

}
