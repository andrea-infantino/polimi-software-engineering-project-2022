package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;

/**
 * Class that represent the phase in which team is built
 */
public class SetUpTeams implements GameControllerState {

    @Override
    public void nextState(GameController g) {
        g.setState(new Initialize());
    }

    @Override
    public void Action(Object o_playerId, Object o_teamId) {
        int playerId = (Integer) o_playerId;
        int teamId = (Integer) o_teamId;
        Server.game.getTeamById(teamId).addPlayer(playerId);
    }
}
