package it.polimi.ingsw.resources;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.Destination;

import java.io.Serializable;

public class ActionPhase1Move implements Serializable {
    private Color student;
    private Destination destination;
    private int islandId;

    private ActionPhase1Move(Color student, Destination destination, int islandId) {
        this.student = student;
        this.destination = destination;
        this.islandId = islandId;
    }

    public Destination getDestination() {
        return destination;
    }

    public Color getStudent() {
        return student;
    }

    public int getIslandId() {
        return islandId;
    }

    public static ActionPhase1Move onIsland(Color student, int islandId) {
        return new ActionPhase1Move(student, Destination.ISLAND, islandId);
    }
    public static ActionPhase1Move inDiningRoom(Color student) {
        return new ActionPhase1Move(student, Destination.DINING_ROOM, -1);
    }

}

/*
ActionPhase1Move move;
if(move.getDestination() == ISLAND){

        }
else {

        }

 */

