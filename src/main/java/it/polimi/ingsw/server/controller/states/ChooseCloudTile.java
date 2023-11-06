package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.Cloud;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;

import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent the choice about Cloud made by the player
 */
public class ChooseCloudTile implements GameControllerState {
    public boolean mustEndRound;

    @Override
    public void nextState(GameController g) {
        if (mustEndRound)
            g.setState(new NextRoundOrEndGame());
        else
            g.setState(new MoveStudents());
    }

    /**
     * Class ChooseCloudTile constructor
     */
    public ChooseCloudTile() {
        mustEndRound = false;
    }

    @Override
    public void Action(Object o_playerId, Object o_cloudId) {
        List<Color> studentsInCloudChosen = new ArrayList<>(Server.game.getBoard().getCloudById((Integer)o_cloudId).getStudents());
        Server.game.getBoard().getCloudById((Integer)o_cloudId).removeStudents(studentsInCloudChosen);
        Server.game.getPlayerById((Integer) o_playerId).getSchool().addStudents(studentsInCloudChosen);
    }
}
