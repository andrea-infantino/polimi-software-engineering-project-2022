package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ConnectionScreen {
    @FXML
    TextField ip;
    @FXML
    TextField port;
    @FXML
    Button button, okButton;
    @FXML
    VBox window;
    @FXML
    StackPane popup;
    @FXML
    Rectangle rectangle;
    @FXML
    private EventHandler enter;

    public void initialize() {
        rectangle.setHeight(App.getCurrentApp().getCurrentStage().getHeight());
        rectangle.setWidth(App.getCurrentApp().getCurrentStage().getWidth());
        popup.setVisible(false);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    if(!ip.getText().isEmpty() && !port.getText().isEmpty()) {
                        connect();
                        event.consume();
                    }
                });
        enter = (EventHandler<KeyEvent>) event -> {
            if(event.getCode() == KeyCode.ENTER && !ip.getText().isEmpty() && !port.getText().isEmpty()) {
                connect();
                event.consume();
            }
        };
        App.getCurrentApp().getCurrentStage().addEventHandler(KeyEvent.KEY_PRESSED, enter);
        window.setOpacity(0.0);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), window);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    @FXML
    private void connect(){
        App.getCurrentApp().getCurrentStage().removeEventHandler(KeyEvent.KEY_PRESSED, enter);
        Client.ip = this.ip.getText();
        try {
            Client.port = Integer.parseInt(this.port.getText());
        }
        catch (NumberFormatException e) {
            showPopup();
        }
        Client.connectionIndex.addListener((observableValue, oldNumber, newNumber) -> {
            if (oldNumber.intValue() == 0 && newNumber.intValue() == -1)
                showPopup();
            if(oldNumber.intValue() == 0 && newNumber.intValue() == 1) {
                App.getCurrentApp().getCurrentStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        App.getCurrentApp().client.getServerHandler().stop();
                    }
                });
                App.getCurrentApp().switchToLoadingScreen();
            }
        });
        App.getCurrentApp().client.run();
    }

    @FXML
    private void showPopup() {
        popup.setVisible(true);
        window.setVisible(false);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    returnToConnectionScreen();
                    event.consume();
                });
    }

    @FXML
    private void returnToConnectionScreen() {
        popup.setVisible(false);
        window.setVisible(true);
        enter = (EventHandler<KeyEvent>) event -> {
            if(event.getCode() == KeyCode.ENTER && !ip.getText().isEmpty() && !port.getText().isEmpty()) {
                connect();
                event.consume();
            }
        };
        App.getCurrentApp().getCurrentStage().addEventHandler(KeyEvent.KEY_PRESSED, enter);
    }
}
