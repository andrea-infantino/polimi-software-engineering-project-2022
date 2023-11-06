package it.polimi.ingsw.server.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * class that represent a single cloud
 */
public class Cloud extends StudentPosition implements Serializable {
    private int id;

    /**
     * constructor
     * @param playerNumber is the number of players playing the game
     * @param id is the single cloud's unique identifier
     */
    public Cloud(int playerNumber, int id) {
        super.init();
        this.id = id;
        if(playerNumber == 2 || playerNumber == 4)
            super.setMaxNumber(3);
        if(playerNumber == 3)
            super.setMaxNumber(4);
    }

    /**
     * request for the cloud's id
     * @return the cloud's id
     */
    public int getId() { return id; }
}
