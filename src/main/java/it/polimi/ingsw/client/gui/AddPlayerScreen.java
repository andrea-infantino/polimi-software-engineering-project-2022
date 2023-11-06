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

import java.util.Objects;

public class AddPlayerScreen {
    @FXML
    VBox window;
    @FXML
    StackPane popup;
    @FXML
    Rectangle rectangle;
    @FXML
    Button button, okButton;
    @FXML
    TextField name;
    @FXML
    Label popupText;


    private Timeline timeline;

    public void initialize() {
        rectangle.setHeight(App.getCurrentApp().getCurrentStage().getHeight());
        rectangle.setWidth(App.getCurrentApp().getCurrentStage().getWidth());
        popup.setVisible(false);

        button.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    if(!name.getText().isEmpty()) {
                        tryJoin();
                        event.consume();
                    }
                });

    }

    @FXML
    private void tryJoin() {
        App.getCurrentApp().client.getServerHandler().sendMessage(new Message(MessageCode.ADD_NEW_PLAYER, name.getText(), null));
        timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(!(App.getCurrentApp().client.preLoadingScreenSwitch == ScreenSwitch.NONE)) {
                                    switch (App.getCurrentApp().client.preLoadingScreenSwitch) {
                                        case SERVER_FULL -> serverFull();
                                        case CREATING_GAME -> creatingGame();
                                        case PLAYER_ADDED, ACK-> App.getCurrentApp().switchToLoadingScreen();
                                        default -> System.out.println(App.getCurrentApp().client.preLoadingScreenSwitch);
                                    }
                                    App.getCurrentApp().client.preLoadingScreenSwitch = ScreenSwitch.NONE;
                                    timeline.stop();
                                }
                            }
                        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void serverFull(){
        popupText.setText("The server is full...");
        okButton.setText("Exit");
        window.setVisible(false);
        popup.setVisible(true);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    App.getCurrentApp().getCurrentStage().close();
                });
    }

    private void creatingGame(){
        popupText.setText("game in creation, try later");
        okButton.setText("Ok");
        window.setVisible(false);
        popup.setVisible(true);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    popup.setVisible(false);
                    window.setVisible(true);
                });
    }
}
