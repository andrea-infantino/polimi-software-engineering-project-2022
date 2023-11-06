package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Team;
import it.polimi.ingsw.server.model.expert.CharacterCard;
import it.polimi.ingsw.server.model.expert.ExpertGame;

/**
 * Class that represent the first state of the game
 */
public class StartGame implements GameControllerState {

    @Override
    public void nextState(GameController g) {
        g.setState(new AddPlayer());
    }

    @Override
    public void Action(Object o_expertMode, Object o_playerNumber)  {
       boolean expertMode = (Boolean) o_expertMode;
       int playerNumber = (Integer) o_playerNumber;
       if(!expertMode)
           Server.game = new Game(false, playerNumber);
       else {
           Server.game = new ExpertGame(true, playerNumber);
           for (CharacterCard c : ((ExpertGame) Server.game).getCharacterCards())
               switch (c.getId()) {
                   case 0: case 10: {
                       c.addStudents(Server.game.getBoard().getBag().extract(4));
                       break;
                   }
                   case 6: {
                       c.addStudents(Server.game.getBoard().getBag().extract(6));
                       break;
                   }
               }
       }
       if(playerNumber == 4){
           Server.game.addTeam(new Team(0));
           Server.game.addTeam(new Team(1));
       }
       System.out.println("Game created with parameters: expertMode = " + expertMode + ", playerNumber = " + playerNumber);
    }
}
