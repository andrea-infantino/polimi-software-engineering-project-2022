package it.polimi.ingsw.client.gui;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class StartScreen {
    @FXML
    BorderPane titleScreen;
    @FXML
    Label text;

    public void initialize() {
        App.getCurrentApp().getCurrentStage().addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                start();
                event.consume();
                App.getCurrentApp().getCurrentStage().removeEventHandler(KeyEvent.KEY_PRESSED, this);
            }
        });
        FadeTransition ft = new FadeTransition(Duration.millis(1000), text);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

    @FXML
    private void start() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), titleScreen);
        ft.setDelay(Duration.ZERO);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
        App.getCurrentApp().switchToConnectionScreen();
        //App.getCurrentApp().switchToBoardScreen();
    }
}