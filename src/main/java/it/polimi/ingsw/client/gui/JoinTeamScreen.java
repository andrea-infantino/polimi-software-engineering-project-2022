package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.enumerators.MessageCode;
import it.polimi.ingsw.resources.enumerators.ScreenSwitch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class JoinTeamScreen {
    @FXML
    VBox window;
    @FXML
    StackPane popup;
    @FXML
    Rectangle rectangle;
    @FXML
    Button buttonWhite, buttonBlack, okButton;

    private Timeline timeline;

    public void initialize(){
        rectangle.setHeight(App.getCurrentApp().getCurrentStage().getHeight());
        rectangle.setWidth(App.getCurrentApp().getCurrentStage().getWidth());
        popup.setVisible(false);

        buttonWhite.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    tryJoin(0);
                    event.consume();
                });
        buttonBlack.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    tryJoin(1);
                    event.consume();
                });
    }

    @FXML
    private void tryJoin(int id) {
        App.getCurrentApp().client.getServerHandler().sendMessage(new Message(MessageCode.ADD_PLAYER_IN_TEAM, App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId(), id));
        timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(!(App.getCurrentApp().client.preLoadingScreenSwitch == ScreenSwitch.NONE)) {
                                    switch (App.getCurrentApp().client.preLoadingScreenSwitch) {
                                        case TEAM_FULL -> teamFull();
                                        case ACK -> App.getCurrentApp().switchToLoadingScreen();
                                        default -> System.out.println("Error");
                                    }
                                    App.getCurrentApp().client.preLoadingScreenSwitch = ScreenSwitch.NONE;
                                    timeline.stop();
                                }
                            }
                        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void teamFull() {
        window.setVisible(false);
        popup.setVisible(true);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    popup.setVisible(false);
                    window.setVisible(true);
                });
    }
}
