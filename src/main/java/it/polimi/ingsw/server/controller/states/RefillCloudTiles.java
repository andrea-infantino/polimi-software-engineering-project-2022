package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.Cloud;

/**
 * Class that represent the refilling of clouds
 */
public class RefillCloudTiles implements GameControllerState {

    @Override
    public void nextState(GameController g) {
        g.setState(new PlayAssistantCard());
    }

    @Override
    public void Action(Object nullObject1, Object nullObject2) {
        for(Cloud cloud : Server.game.getBoard().getClouds())
            Server.game.getBoard().refillCloud(cloud);
    }
}
