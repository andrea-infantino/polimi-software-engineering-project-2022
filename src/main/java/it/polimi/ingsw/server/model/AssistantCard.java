package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.lang.Math;

/**
 * class that represent a single assistant card
 */
public class AssistantCard implements Serializable {
    private int speedValue;
    private int moveValue;

    /**
     * request for the card's speed value
     * @return the speed value
     */
    public int getSpeedValue(){
        return speedValue;
    }

    /**
     * request for the card's move value
     * @return the move value
     */
    public int getMoveValue(){
        return moveValue;
    }

    /**
     * constructor
     * @param speedValue of the created card
     */
    public AssistantCard(int speedValue){
        this.speedValue = speedValue;
        this.moveValue = (int) Math.ceil((double) speedValue/2);
    }
}
