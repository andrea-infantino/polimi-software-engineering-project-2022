package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Team;
import it.polimi.ingsw.server.model.expert.ExpertPlayer;

public class AddPlayer implements GameControllerState {

    @Override
    public void nextState(GameController g) {
        if(Server.game.getPlayerNumber() == 2 || Server.game.getPlayerNumber() == 3)
            g.setState(new Initialize());
        if(Server.game.getPlayerNumber() == 4)
            g.setState(new SetUpTeams());
    }

    @Override
    public void Action(Object o_playerName, Object o_playerId) {
        Player player;
        if(Server.game.isExpertMode())
            player = new ExpertPlayer((Integer) o_playerId, (String) o_playerName);
        else
            player = new Player((Integer) o_playerId, (String) o_playerName);
        Server.game.addPlayer(player);
        if(Server.game.getPlayerNumber() == 2 || Server.game.getPlayerNumber() == 3)
            Server.game.addTeam(new Team((Integer) o_playerId, (Integer) o_playerId, Server.game.getPlayerNumber()));
        if(Server.game.getPlayerNumber() == 3)
            Server.game.getPlayerById((Integer) o_playerId).getSchool().addStudents(Server.game.getBoard().getBag().extract(9));
        else {
            Server.game.getPlayerById((Integer) o_playerId).getSchool().addStudents(Server.game.getBoard().getBag().extract(7));
        }
    }
}
