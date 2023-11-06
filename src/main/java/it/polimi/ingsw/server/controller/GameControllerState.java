package it.polimi.ingsw.server.controller;

/**
 * interface that represents a state of the FSA
 */
public interface GameControllerState {
    void nextState(GameController g);
    void Action(Object o1, Object o2);

}
