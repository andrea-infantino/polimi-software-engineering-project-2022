package it.polimi.ingsw.client.gui;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class EndGameScreen {

    @FXML
    Label textTop, textDown;

    @FXML
    ImageView cloudImage, playerImage1, playerImage2;

    @FXML
    BorderPane endGame;

    public void initialize() {
        if(App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNumber() != 4){
            textDown.setText("The winner is: " + App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNames().get(App.getCurrentApp().client.getServerObserver().winners.get(0)));
            playerImage1.setImage(new Image("/game/graphics/animals/backs/back_" + App.getCurrentApp().client.getServerObserver().winners.get(0) + ".png"));
            playerImage1.setTranslateX(75);
        }
        else{
            textDown.setText("The winners are: " + App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNames().get(App.getCurrentApp().client.getServerObserver().winners.get(0)) + "and" + App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNames().get(App.getCurrentApp().client.getServerObserver().winners.get(1)));
            playerImage1.setImage(new Image("/game/graphics/animals/backs/back_" + App.getCurrentApp().client.getServerObserver().winners.get(0) + ".png"));
            playerImage2.setImage(new Image("/game/graphics/animals/backs/back_" + App.getCurrentApp().client.getServerObserver().winners.get(1) + ".png"));
            playerImage1.setTranslateX(20);
            playerImage2.setTranslateX(135);
        }

    }



}
