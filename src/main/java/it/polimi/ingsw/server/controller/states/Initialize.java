package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.GameRound;

import java.util.ArrayList;
import java.util.List;

/**
 * Class in which the game is initialized
 */
public class Initialize implements GameControllerState {

    @Override
    public void nextState(GameController g) {
        g.setState(new RefillCloudTiles());
    }

    @Override
    public void Action(Object nullObject1, Object nullObject2) {
        int firstPlayerId = (int) (Math.random() * Server.game.getPlayerNumber());
        List<Integer> currentRound = new ArrayList<>();
        int i;
        for(i=firstPlayerId; i<Server.game.getPlayerNumber(); i++)
            currentRound.add(i);
        for(i=0; i<firstPlayerId; i++)
            currentRound.add(i);
        Server.game.currentRound = new GameRound(currentRound);
    }
}
