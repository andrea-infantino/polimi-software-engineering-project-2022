package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.resources.enumerators.ScreenSwitch;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static App currentScreen;
    private Stage primaryStage;
    public Client client;

    public static void main(String[] args) {launch(args);}

    public static App getCurrentApp() {
        return currentScreen;
    }

    @Override
    public void start(Stage stage) {
        currentScreen = this;
        primaryStage = stage;
        primaryStage.setMaximized(true);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setTitle("Eriantys");

        switchToTitleScreen();
        primaryStage.show();
        client = new Client();
        client.loadingScreenSwitch = ScreenSwitch.NONE;
        client.preLoadingScreenSwitch = ScreenSwitch.NONE;
    }

    public Stage getCurrentStage() {
        return primaryStage;
    }

    public void switchToTitleScreen() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/StartScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        sc.setCursor(new ImageCursor(new Image("normal.png")));
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
    }

    public void switchToConnectionScreen() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/ConnectionScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        sc.setCursor(new ImageCursor(new Image("normal.png")));
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
    }

    public void switchToLoadingScreen() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/LoadingScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        sc.setCursor(new ImageCursor(new Image("normal.png")));
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
    }

    public void switchToGameCreationScreen(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/GameCreationScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        sc.setCursor(new ImageCursor(new Image("normal.png")));
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
    }

    public void switchToAddPlayerScreen(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/AddPlayerScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        sc.setCursor(new ImageCursor(new Image("normal.png")));
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
    }

    public void switchToJoinTeamScreen(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/JoinTeamScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        sc.setCursor(new ImageCursor(new Image("normal.png")));
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
    }

    public void switchToBoardScreen() {
        Parent root;
        try {
            if(App.getCurrentApp().client.getServerObserver().getInfo().isExpertMode())
                root = FXMLLoader.load(getClass().getResource("/game/ExpertGameScreen.fxml"));
            else
                root = FXMLLoader.load(getClass().getResource("/game/NormalGameScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        sc.setCursor(new ImageCursor(new Image("normal.png")));
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.setFullScreen(false);
    }

    public void switchToEndScreen() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/EndGame.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        sc.setCursor(new ImageCursor(new Image("normal.png")));
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
        primaryStage.setFullScreen(false);
    }
}
