package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.PlayerTurn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstablishRoundOrder implements GameControllerState {

    @Override
    public void nextState(GameController g) {
        g.setState(new MoveStudents());
    }

    @Override
    public void Action(Object nullObject1, Object nullObject2) {
        Map<Integer, AssistantCard> assistantCardsPlayed = new HashMap<>(Server.game.currentRound.getAssistantCardsPlayed());
        List<Integer> order = new ArrayList<>();

        while(!assistantCardsPlayed.isEmpty()) {
            int min = 11;
            Integer minPlayerId = null;
            for (Integer playerId : assistantCardsPlayed.keySet())
                if(assistantCardsPlayed.get(playerId).getSpeedValue() < min) {
                    min = assistantCardsPlayed.get(playerId).getSpeedValue();
                    minPlayerId = playerId;
                }
            order.add(minPlayerId);
            assistantCardsPlayed.remove(minPlayerId);
        }

        Server.game.currentRound.setOrder(order);
    }
}
