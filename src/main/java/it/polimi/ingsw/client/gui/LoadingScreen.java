package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.resources.enumerators.ScreenSwitch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Objects;

public class LoadingScreen {
    @FXML
    Rectangle rectangle;

    private Timeline timeline;

    public void initialize() {
        rectangle.setHeight(App.getCurrentApp().getCurrentStage().getHeight());
        rectangle.setWidth(App.getCurrentApp().getCurrentStage().getWidth());

        timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(!(App.getCurrentApp().client.loadingScreenSwitch == ScreenSwitch.NONE)) {
                                    switch (App.getCurrentApp().client.loadingScreenSwitch) {
                                        case GAME_CREATION -> App.getCurrentApp().switchToGameCreationScreen();
                                        case ADD_PLAYER -> App.getCurrentApp().switchToAddPlayerScreen();
                                        case JOIN_TEAM -> App.getCurrentApp().switchToJoinTeamScreen();
                                        case BOARD -> App.getCurrentApp().switchToBoardScreen();
                                        default -> System.out.println("Error");
                                    }
                                    App.getCurrentApp().client.loadingScreenSwitch = ScreenSwitch.NONE;
                                    timeline.stop();
                                }
                            }
                        }));
        timeline.setDelay(Duration.millis(1000));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
