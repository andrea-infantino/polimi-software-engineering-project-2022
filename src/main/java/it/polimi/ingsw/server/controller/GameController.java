package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.states.StartGame;

/**
 * Class that represent the game controller
 */
public class GameController {
    private GameControllerState state;

    /**
     * constructor
     */
    public GameController() {
        state = new StartGame();
    }

    /**
     * request of the state in which the controller is
     * @return current state
     */
    public GameControllerState getState() {
        return state;
    }

    /**
     * set the state of the controller
     * @param state state to be set
     */
    public void setState(GameControllerState state) {
        this.state = state;
    }

    /**
     * method that sets the next state of the FSA
     */
    public void nextState() {
        state.nextState(this);
    }

    /**
     * method that changes the model through the action of every state
     * @param o1 generic object that will be casted
     * @param o2 generic object that will be casted
     */
    public void changeModel(Object o1, Object o2) {
        state.Action(o1, o2);
    }

}
