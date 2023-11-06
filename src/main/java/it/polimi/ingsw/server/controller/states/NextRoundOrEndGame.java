package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.Cloud;
import it.polimi.ingsw.server.model.Player;

import java.util.HashMap;

/**
 * class that checks whether to start a new round or to end the game
 */
public class NextRoundOrEndGame implements GameControllerState {
    @Override
    public void nextState(GameController g) {
        if (checkEndConditions())
            g.setState(new EndGame());
        else
            g.setState(new RefillCloudTiles());
    }

    @Override
    public void Action(Object o1, Object o2) {
        Server.game.currentRound.roundCounter++;
        for(Cloud c : Server.game.getBoard().getClouds())
            System.out.println("Cloud: " + c.getStudents() + "\n");
    }

    /**
     * check if the end conditions are reached
     * @return true if the game is in end conditions, false otherwise
     */
    public boolean checkEndConditions(){
        if (Server.game.getBoard().getBag().isEmpty())
            return true;
        for (Player p : Server.game.getPlayers()){
            if (p.emptyDeck())
                return true;
        }
        return false;
    }

}
