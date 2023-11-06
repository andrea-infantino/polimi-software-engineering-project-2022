package it.polimi.ingsw.server.model.expert;

import it.polimi.ingsw.server.model.StudentPosition;

import java.io.Serializable;

/**
 * class that represents a single Character card, used only if the game is in expert mode
 */
public class CharacterCard extends StudentPosition implements Serializable {
    private int cost;
    private int id;

    public int remainingNoEntryTiles;

    /**
     * request for the character card's id
     * @return the character card's id
     */
    public int getId() {
        return id;
    }

    /**
     * request for the character card's cost
     * @return the character card's cost
     */
    public int getCost(){
        return cost;
    }

    /**
     * method that increases the cost of the card every time is played
     */
    public void increaseCost(){
        cost++;
    }

    /**
     * constructor
     * @param id the unique identifier of the character card created
     */
    public CharacterCard (int id){
        this.id = id;
        super.init();
        super.setMaxNumber(10);
        switch (id){
            case 0:
            case 3:
            case 6:
            case 9:
                remainingNoEntryTiles = -1; cost = 1; break;
            case 1:
            case 7:
            case 10:
                remainingNoEntryTiles = -1; cost = 2; break;
            case 2:
            case 5:
            case 8:
            case 11:
                remainingNoEntryTiles = -1; cost = 3; break;
            case 4: remainingNoEntryTiles = 4; cost = 2; break;
        }
    }
}
