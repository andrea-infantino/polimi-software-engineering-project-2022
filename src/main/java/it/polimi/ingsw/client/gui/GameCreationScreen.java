package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.enumerators.MessageCode;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameCreationScreen {
    @FXML
    ToggleButton p3, p4, E;
    @FXML
    Button button;
    @FXML
    private EventHandler enter;

    public void initialize() {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                        initGame();
                        event.consume();
                });
        enter = (EventHandler<KeyEvent>) event -> {
            if(event.getCode() == KeyCode.ENTER) {
                initGame();
                event.consume();
            }
        };
        App.getCurrentApp().getCurrentStage().addEventHandler(KeyEvent.KEY_PRESSED, enter);
    }

    @FXML
    private void initGame() {
        App.getCurrentApp().getCurrentStage().removeEventHandler(KeyEvent.KEY_PRESSED, enter);
        boolean expertMode;
        int playerNumber;
        if(p3.isSelected())
            playerNumber = 3;
        else
            if(p4.isSelected())
                playerNumber = 4;
            else
                playerNumber = 2;

        if(E.isSelected())
            expertMode = true;
        else
            expertMode = false;

        App.getCurrentApp().client.getServerHandler().sendMessage(new Message(MessageCode.EXPERT_MODE_AND_PLAYER_NUMBER, expertMode, playerNumber));
        App.getCurrentApp().switchToLoadingScreen();
    }
}
