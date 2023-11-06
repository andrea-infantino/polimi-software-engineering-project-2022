package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.AssistantCard;

/**
 * Class that represent the state in which a player use an assistant card
 */
public class PlayAssistantCard implements GameControllerState {

    @Override
    public void nextState(GameController g) {
        g.setState(new EstablishRoundOrder());
    }

    @Override
    public void Action(Object o_playerId, Object o_assistantCardSpeedValue) {
        int playerId = (Integer) o_playerId;
        int assistantCardSpeedValue = (Integer) o_assistantCardSpeedValue;
        AssistantCard assistantCard = Server.game.getPlayerById(playerId).getAssistantCardBySpeedValue(assistantCardSpeedValue);
        Server.game.playCard(Server.game.getPlayerById(playerId), assistantCard);
    }
}
