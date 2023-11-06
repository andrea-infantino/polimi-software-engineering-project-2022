package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.PlayerInfo;
import it.polimi.ingsw.client.gui.App;
import it.polimi.ingsw.resources.ActionPhase1Move;
import it.polimi.ingsw.resources.GameState;
import it.polimi.ingsw.resources.enumerators.*;
import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Player;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.util.Duration;


import java.net.URISyntaxException;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static it.polimi.ingsw.resources.enumerators.Destination.DINING_ROOM;
import static it.polimi.ingsw.resources.enumerators.Destination.ISLAND;
import static it.polimi.ingsw.resources.enumerators.TowerColor.*;


public class NormalGameScreen {

    GameState gameState;
    Timeline timeline;

    @FXML
    VBox deckContainer;
    @FXML
    Group deck, clouds, schoolHeader, school, assistantCardsPlayed, deckZone;
    @FXML
    Group islands;
    @FXML
    Rectangle diningRoom, zone;
    @FXML
    ImageView schoolImage;
    @FXML
    Label messages;

    Map<Integer, EventHandler> eventHandlersEntered;
    Map<Integer, EventHandler> eventHandlersExited;
    int mySchoolIndex;
    AtomicBoolean canContinue;

    public void initialize() {
        //playerNumber = App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNumber();
        //playerId = App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId();
        this.gameState = App.getCurrentApp().client.getServerObserver().gameState;
        canContinue = new AtomicBoolean(true);
        Platform.runLater(() -> {
            showMessages();
            showClouds();
            showSchool();
            showDeck();
            showAssistantCardsPlayed();
            showIslands();

        timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                        new EventHandler<ActionEvent>() {
                            private GamePhase gamePhase = null;
                            @Override
                            public void handle(ActionEvent event) {
                                    if(App.getCurrentApp().client.getServerObserver().gamePhase == GamePhase.END_GAME ){
                                        timeline.stop();
                                        App.getCurrentApp().switchToEndScreen();
                                    }
                                    if(App.getCurrentApp().client.getServerObserver().mustRefresh.get()){
                                        App.getCurrentApp().client.getServerObserver().mustRefresh.set(false);
                                        timeline.stop();
                                        App.getCurrentApp().switchToBoardScreen();
                                    }
                                    if (App.getCurrentApp().client.getServerObserver().gamePhase != gamePhase) {
                                        gamePhase = App.getCurrentApp().client.getServerObserver().gamePhase;
                                        switch (gamePhase) {
                                            case PLANNING_PHASE -> planningPhase();
                                            case ACTION_PHASE1 -> moveStudents();
                                            case ACTION_PHASE2 -> moveMotherNature();
                                            case ACTION_PHASE3 -> chooseCloud();

                                        }
                                    }
                            }
                        }));
        timeline.setDelay(Duration.millis(500));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        });
    }

    private void showMessages() {
        AtomicBoolean[] canWrite = {new AtomicBoolean(true)};
        timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(!App.getCurrentApp().client.getServerObserver().messages.isEmpty() && canWrite[0].get()){
                                    try {
                                        canWrite[0].set(false);
                                        String message = App.getCurrentApp().client.getServerObserver().messages.get(0);
                                        messages.setText(message);
                                        messages.setOpacity(1);
                                        messages.setVisible(true);
                                        FadeTransition ft = new FadeTransition(Duration.millis(500), messages);
                                        ft.setFromValue(0);
                                        ft.setToValue(1);
                                        ft.setCycleCount(1);
                                        ft.setAutoReverse(false);
                                        ft.play();
                                        FadeTransition ft1 = new FadeTransition(Duration.millis(500), messages);
                                        ft1.setFromValue(1);
                                        ft1.setToValue(0);
                                        ft1.setCycleCount(1);
                                        ft1.setAutoReverse(false);
                                        ft1.setDelay(Duration.millis(1500));
                                        ft1.play();
                                        Timeline timer2 = new Timeline(new KeyFrame(Duration.millis(1500), event1 -> {
                                            try {
                                                messages.setVisible(false);
                                                messages.setOpacity(0);
                                                App.getCurrentApp().client.getServerObserver().messages.remove(0);
                                                canWrite[0].set(true);
                                            }
                                            catch (IndexOutOfBoundsException ignored) {}
                                        }));
                                        timer2.setCycleCount(1);
                                        timer2.setDelay(Duration.millis(2000));
                                        timer2.play();
                                    }
                                    catch (IndexOutOfBoundsException ignored) {}
                                }
                            }
                        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void planningPhase() {
        if(App.getCurrentApp().client.getServerObserver().currentPlayerId == App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId())
            for(int i=0; i<(deck.getChildren().size()-1); i++){
                int finalI = i;
                deck.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_CLICKED,
                        event -> {
                            StringBuilder id = new StringBuilder(((ImageView) deck.getChildren().get(finalI)).getId());
                            id.delete(0, 10);
                            int speedValue = Integer.parseInt(id.toString());
                            App.getCurrentApp().client.getServerObserver().playAssistantCard(speedValue);
                        });
            }
    }

    private void moveStudents() {
        if(App.getCurrentApp().client.getServerObserver().currentPlayerId == App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId()){ //TODO metti il giocatore corretto
            int movesNumber;
            if (App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNumber() == 3)
                movesNumber = 4;
            else
                movesNumber = 3;
            List<ActionPhase1Move> moves = new ArrayList<>();
            Color[] color = new Color[1];
            int[] studentIndex = {-1};
            Destination[] destination = new Destination[1];
            boolean[] flags = {true, false};
            int[] islandId = new int[1];
            int[] moveCounter = {0};
            for (Node student : ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren()) {
                student.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    if(flags[0] && canContinue.get())
                        student.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                });
                student.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                    if(flags[0] && canContinue.get())
                        student.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                });
                student.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (flags[0] && canContinue.get()) {
                            student.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            studentIndex[0] = ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren().indexOf(student);
                            switch (((ImageView) student).getImage().getUrl().substring(((ImageView) student).getImage().getUrl().length()-18)) {
                                case "ts/Red_Student.png" -> color[0] = Color.RED;
                                case "s/Blue_Student.png" -> color[0] = Color.BLUE;
                                case "/Green_Student.png" -> color[0] = Color.GREEN;
                                case "Yellow_Student.png" -> color[0] = Color.YELLOW;
                                case "s/Pink_Student.png" -> color[0] = Color.PINK;
                            }
                            flags[0] = false;
                            flags[1] = true;
                            for (Node island : islands.getChildren()) {
                                island.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                                island.addEventHandler(MouseEvent.MOUSE_EXITED, event1 -> {
                                    if (flags[1])
                                        island.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.5, 0.0, 0.0);");
                                });
                                island.addEventHandler(MouseEvent.MOUSE_ENTERED, event1 -> {
                                    if (flags[1])
                                        island.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                                });
                            }
                            schoolImage.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            diningRoom.addEventHandler(MouseEvent.MOUSE_EXITED, event1 -> {
                                if (flags[1])
                                    schoolImage.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.5, 0.0, 0.0);");
                            });
                            diningRoom.addEventHandler(MouseEvent.MOUSE_ENTERED, event1 -> {
                                if (flags[1])
                                    schoolImage.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                            });
                        }
                    }
                });
            }
            for (Node island : islands.getChildren()) {
                island.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (flags[1]) {
                            islandId[0] = Integer.parseInt(island.getId().substring(7));
                            ActionPhase1Move move = ActionPhase1Move.onIsland(color[0], islandId[0]);
                            moves.add(move);
                            ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren().get(studentIndex[0]).setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            for (Node is : islands.getChildren())
                                is.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            schoolImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            moveCounter[0]++;
                            if (moveCounter[0] == movesNumber){
                                //removeAllLocal();
                                App.getCurrentApp().client.getServerObserver().moveStudents(moves);
                            }
                            ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren().get(studentIndex[0]).setVisible(false);
                            //addLocalStudentOnIsland(color[0]);
                            flags[0] = true;
                            flags[1] = false;
                        }
                    }
                });
            }
            diningRoom.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (flags[1]) {
                        ActionPhase1Move move = ActionPhase1Move.inDiningRoom(color[0]);
                        moves.add(move);
                        ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren().get(studentIndex[0]).setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                        for (Node island : islands.getChildren())
                            island.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                        schoolImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                        moveCounter[0]++;
                        if (moveCounter[0] == movesNumber) {
                            //removeAllLocal();
                            App.getCurrentApp().client.getServerObserver().moveStudents(moves);
                        }
                        ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren().get(studentIndex[0]).setVisible(false);
                        //addLocalStudentInDiningRoom(color[0]);
                        flags[0] = true;
                        flags[1] = false;
                    }
                }
            });
        }
    }

    private void moveMotherNature(){
        if(App.getCurrentApp().client.getServerObserver().currentPlayerId == App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId()) {
            List<Integer> possibleIslands = new ArrayList<>();
            int moves = gameState.getCardsPlayed().get(App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId()).getMoveValue();
            if (App.getCurrentApp().client.getServerObserver().card4played)
                moves = moves + 2;
            int islandCounter = -1;
            for (GameState.Board.Island is : gameState.getBoard().getIslands())
                if (is.isHasMotherNature())
                    islandCounter = is.getId() + 1;
            for (int i = 0; i < moves; i++) {
                if (islandCounter == gameState.getBoard().getIslands().size())
                    islandCounter = 0;
                if (!possibleIslands.contains(islandCounter))
                    possibleIslands.add(islandCounter);
                islandCounter++;
            }
            for (Node island : islands.getChildren())
                if (possibleIslands.contains(Integer.parseInt(island.getId().substring(7)))) {
                    island.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                    island.addEventHandler(MouseEvent.MOUSE_EXITED, event1 -> {
                        if(canContinue.get())
                        island.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.5, 0.0, 0.0);");
                    });
                    island.addEventHandler(MouseEvent.MOUSE_ENTERED, event1 -> {
                        if(canContinue.get())
                        island.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                    });
                    island.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(canContinue.get())
                        App.getCurrentApp().client.getServerObserver().moveMotherNature(Integer.parseInt(island.getId().substring(7)));
                    });
                }
        }
    }

    private void chooseCloud(){
        if(App.getCurrentApp().client.getServerObserver().currentPlayerId == App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId())
        for (Node cloud : clouds.getChildren()){
            if(!gameState.getBoard().getClouds().get(Integer.parseInt(cloud.getId().substring(6))).getStudents().isEmpty()) {
                cloud.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                cloud.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    if(canContinue.get())
                    cloud.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                });
                cloud.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                    if(canContinue.get())
                    cloud.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                });
                cloud.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if(canContinue.get())
                    App.getCurrentApp().client.getServerObserver().chooseCloud(Integer.parseInt(cloud.getId().substring(6)));
                });
            }
        }
    }

    private void showIslands() {
        for(GameState.Board.Island i : gameState.getBoard().getIslands()){
            Group island = new Group();

            island.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            island.setId("island_"+i.getId());

            int n = (int) (Math.random() * 3);
            n++;
            ImageView image = new ImageView();
            if(n==1)
                image.setImage(App.getCurrentApp().client.getServerObserver().getImageLoader().getIsland_1());
            if(n==2)
                image.setImage(App.getCurrentApp().client.getServerObserver().getImageLoader().getIsland_2());
            if(n==3)
                image.setImage(App.getCurrentApp().client.getServerObserver().getImageLoader().getIsland_3());
            image.setFitHeight(130);
            image.setPreserveRatio(true);
            //imposta coordinate in base al numero di isole

            island.getChildren().add(image);

            if(i.isHasMotherNature()) {
                ImageView motherNature = new ImageView(new Image("game/graphics/board/motherNature.png"));
                motherNature.setPreserveRatio(true);
                motherNature.setFitHeight(25);
                motherNature.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                motherNature.setTranslateX(52);
                motherNature.setTranslateY(52);
                island.getChildren().add(motherNature);
            }

            if(i.getControlledBy()!=null) {
                ImageView tower = new ImageView();
                tower.setPreserveRatio(true);
                tower.setFitHeight(38);
                tower.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                Label towerNumber = new Label("" + i.getIslandNumber());
                towerNumber.setFont(new Font("Snap ITC", 15));
                towerNumber.setTextFill(javafx.scene.paint.Color.WHITE);
                towerNumber.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                switch (i.getControlledBy()) {
                    case BLACK -> {
                        tower.setImage(new Image("game/graphics/towers/Black_Tower.png"));
                        towerNumber.setTextFill(javafx.scene.paint.Color.WHITE);
                    }
                    case WHITE -> {
                        tower.setImage(new Image("game/graphics/towers/White_Tower.png"));
                        towerNumber.setTextFill(javafx.scene.paint.Color.BLACK);
                    }
                    case GREY -> {
                        tower.setImage(new Image("game/graphics/towers/Grey_Tower.png"));
                        towerNumber.setTextFill(javafx.scene.paint.Color.WHITE);
                    }
                }
                tower.setTranslateX(52);
                tower.setTranslateY(5);
                towerNumber.setTranslateX(61);
                towerNumber.setTranslateY(15);
                island.getChildren().add(tower);
                island.getChildren().add(towerNumber);
            }


            ImageView redStudentsImage = new ImageView(new Image("game/graphics/students/Red_Student.png"));
            redStudentsImage.setPreserveRatio(true);
            redStudentsImage.setFitHeight(45);
            redStudentsImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            redStudentsImage.setTranslateX(10);
            redStudentsImage.setTranslateY(17);
            ImageView greenStudentsImage = new ImageView(new Image("game/graphics/students/Green_Student.png"));
            greenStudentsImage.setPreserveRatio(true);
            greenStudentsImage.setFitHeight(45);
            greenStudentsImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            greenStudentsImage.setTranslateX(10);
            greenStudentsImage.setTranslateY(60);
            ImageView blueStudentsImage = new ImageView(new Image("game/graphics/students/Blue_Student.png"));
            blueStudentsImage.setPreserveRatio(true);
            blueStudentsImage.setFitHeight(45);
            blueStudentsImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            blueStudentsImage.setTranslateX(42);
            blueStudentsImage.setTranslateY(70);
            ImageView yellowStudentsImage = new ImageView(new Image("game/graphics/students/Yellow_Student.png"));
            yellowStudentsImage.setPreserveRatio(true);
            yellowStudentsImage.setFitHeight(45);
            yellowStudentsImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            yellowStudentsImage.setTranslateX(72);
            yellowStudentsImage.setTranslateY(60);
            ImageView pinkStudentsImage = new ImageView(new Image("game/graphics/students/Pink_Student.png"));
            pinkStudentsImage.setPreserveRatio(true);
            pinkStudentsImage.setFitHeight(45);
            pinkStudentsImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            pinkStudentsImage.setTranslateX(72);
            pinkStudentsImage.setTranslateY(17);

            List<Color> students = new ArrayList<>(i.getStudents());
            int r=0, b=0, y=0, g=0, p=0;
            for(Color s : students)
                switch (s){
                    case BLUE -> b++;
                    case PINK -> p++;
                    case YELLOW -> y++;
                    case RED -> r++;
                    case GREEN -> g++;
                }

            Label redStudentsNumber = new Label(""+r);
            redStudentsNumber.setFont(new Font("Snap ITC", 15));
            redStudentsNumber.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            redStudentsNumber.setTextFill(javafx.scene.paint.Color.RED);
            redStudentsNumber.setTranslateX(27);
            redStudentsNumber.setTranslateY(47);
            Label greenStudentsNumber = new Label(""+g);
            greenStudentsNumber.setFont(new Font("Snap ITC", 15));
            greenStudentsNumber.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            greenStudentsNumber.setTextFill(javafx.scene.paint.Color.GREEN);
            greenStudentsNumber.setTranslateX(27);
            greenStudentsNumber.setTranslateY(90);
            Label blueStudentsNumber = new Label(""+b);
            blueStudentsNumber.setFont(new Font("Snap ITC", 15));
            blueStudentsNumber.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            blueStudentsNumber.setTextFill(javafx.scene.paint.Color.CYAN);
            blueStudentsNumber.setTranslateX(59);
            blueStudentsNumber.setTranslateY(100);
            Label yellowStudentsNumber = new Label(""+y);
            yellowStudentsNumber.setFont(new Font("Snap ITC", 15));
            yellowStudentsNumber.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            yellowStudentsNumber.setTextFill(javafx.scene.paint.Color.YELLOW);
            yellowStudentsNumber.setTranslateX(89);
            yellowStudentsNumber.setTranslateY(90);
            Label pinkStudentsNumber = new Label(""+p);
            pinkStudentsNumber.setFont(new Font("Snap ITC", 15));
            pinkStudentsNumber.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            pinkStudentsNumber.setTextFill(javafx.scene.paint.Color.FUCHSIA);
            pinkStudentsNumber.setTranslateX(89);
            pinkStudentsNumber.setTranslateY(47);

            island.getChildren().add(redStudentsImage);
            island.getChildren().add(redStudentsNumber);
            island.getChildren().add(greenStudentsImage);
            island.getChildren().add(greenStudentsNumber);
            island.getChildren().add(blueStudentsImage);
            island.getChildren().add(blueStudentsNumber);
            island.getChildren().add(yellowStudentsImage);
            island.getChildren().add(yellowStudentsNumber);
            island.getChildren().add(pinkStudentsImage);
            island.getChildren().add(pinkStudentsNumber);

            islands.getChildren().add(island);

        }

        //imposta coordinate in base al numero di gruppi di isole
        switch (islands.getChildren().size()) {
            case 12 -> {
                islands.getChildren().get(0).setTranslateX(-225);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(-75);
                islands.getChildren().get(1).setTranslateY(-225);

                islands.getChildren().get(2).setTranslateX(75);
                islands.getChildren().get(2).setTranslateY(-225);

                islands.getChildren().get(3).setTranslateX(225);
                islands.getChildren().get(3).setTranslateY(-225);

                islands.getChildren().get(4).setTranslateX(225);
                islands.getChildren().get(4).setTranslateY(-75);

                islands.getChildren().get(5).setTranslateX(225);
                islands.getChildren().get(5).setTranslateY(75);

                islands.getChildren().get(6).setTranslateX(225);
                islands.getChildren().get(6).setTranslateY(225);

                islands.getChildren().get(7).setTranslateX(75);
                islands.getChildren().get(7).setTranslateY(225);

                islands.getChildren().get(8).setTranslateX(-75);
                islands.getChildren().get(8).setTranslateY(225);

                islands.getChildren().get(9).setTranslateX(-225);
                islands.getChildren().get(9).setTranslateY(225);

                islands.getChildren().get(10).setTranslateX(-225);
                islands.getChildren().get(10).setTranslateY(75);

                islands.getChildren().get(11).setTranslateX(-225);
                islands.getChildren().get(11).setTranslateY(-75);
            }
            case 11 -> {
                islands.getChildren().get(0).setTranslateX(-150);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(0);
                islands.getChildren().get(1).setTranslateY(-225);

                islands.getChildren().get(2).setTranslateX(150);
                islands.getChildren().get(2).setTranslateY(-225);

                islands.getChildren().get(3).setTranslateX(225);
                islands.getChildren().get(3).setTranslateY(-75);

                islands.getChildren().get(4).setTranslateX(225);
                islands.getChildren().get(4).setTranslateY(75);

                islands.getChildren().get(5).setTranslateX(225);
                islands.getChildren().get(5).setTranslateY(225);

                islands.getChildren().get(6).setTranslateX(75);
                islands.getChildren().get(6).setTranslateY(225);

                islands.getChildren().get(7).setTranslateX(-75);
                islands.getChildren().get(7).setTranslateY(225);

                islands.getChildren().get(8).setTranslateX(-225);
                islands.getChildren().get(8).setTranslateY(225);

                islands.getChildren().get(9).setTranslateX(-225);
                islands.getChildren().get(9).setTranslateY(75);

                islands.getChildren().get(10).setTranslateX(-225);
                islands.getChildren().get(10).setTranslateY(-75);
            }
            case 10 -> {
                islands.getChildren().get(0).setTranslateX(-150);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(0);
                islands.getChildren().get(1).setTranslateY(-225);

                islands.getChildren().get(2).setTranslateX(150);
                islands.getChildren().get(2).setTranslateY(-225);

                islands.getChildren().get(3).setTranslateX(225);
                islands.getChildren().get(3).setTranslateY(-75);

                islands.getChildren().get(4).setTranslateX(225);
                islands.getChildren().get(4).setTranslateY(75);

                islands.getChildren().get(5).setTranslateX(150);
                islands.getChildren().get(5).setTranslateY(225);

                islands.getChildren().get(6).setTranslateX(0);
                islands.getChildren().get(6).setTranslateY(225);

                islands.getChildren().get(7).setTranslateX(-150);
                islands.getChildren().get(7).setTranslateY(225);

                islands.getChildren().get(8).setTranslateX(-225);
                islands.getChildren().get(8).setTranslateY(75);

                islands.getChildren().get(9).setTranslateX(-225);
                islands.getChildren().get(9).setTranslateY(-75);
            }
            case 9 -> {
                islands.getChildren().get(0).setTranslateX(-75);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(75);
                islands.getChildren().get(1).setTranslateY(-225);

                islands.getChildren().get(2).setTranslateX(225);
                islands.getChildren().get(2).setTranslateY(-75);

                islands.getChildren().get(3).setTranslateX(225);
                islands.getChildren().get(3).setTranslateY(75);

                islands.getChildren().get(4).setTranslateX(150);
                islands.getChildren().get(4).setTranslateY(225);

                islands.getChildren().get(5).setTranslateX(0);
                islands.getChildren().get(5).setTranslateY(225);

                islands.getChildren().get(6).setTranslateX(-150);
                islands.getChildren().get(6).setTranslateY(225);

                islands.getChildren().get(7).setTranslateX(-225);
                islands.getChildren().get(7).setTranslateY(75);

                islands.getChildren().get(8).setTranslateX(-225);
                islands.getChildren().get(8).setTranslateY(-75);
            }
            case 8 -> {
                islands.getChildren().get(0).setTranslateX(-225);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(0);
                islands.getChildren().get(1).setTranslateY(-225);

                islands.getChildren().get(2).setTranslateX(225);
                islands.getChildren().get(2).setTranslateY(-225);

                islands.getChildren().get(3).setTranslateX(225);
                islands.getChildren().get(3).setTranslateY(0);

                islands.getChildren().get(4).setTranslateX(225);
                islands.getChildren().get(4).setTranslateY(225);

                islands.getChildren().get(5).setTranslateX(0);
                islands.getChildren().get(5).setTranslateY(225);

                islands.getChildren().get(6).setTranslateX(-225);
                islands.getChildren().get(6).setTranslateY(225);

                islands.getChildren().get(7).setTranslateX(-225);
                islands.getChildren().get(7).setTranslateY(0);
            }
            case 7 -> {
                islands.getChildren().get(0).setTranslateX(-112);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(112);
                islands.getChildren().get(1).setTranslateY(-225);

                islands.getChildren().get(2).setTranslateX(225);
                islands.getChildren().get(2).setTranslateY(0);

                islands.getChildren().get(3).setTranslateX(225);
                islands.getChildren().get(3).setTranslateY(225);

                islands.getChildren().get(4).setTranslateX(0);
                islands.getChildren().get(4).setTranslateY(225);

                islands.getChildren().get(5).setTranslateX(-225);
                islands.getChildren().get(5).setTranslateY(225);

                islands.getChildren().get(6).setTranslateX(-225);
                islands.getChildren().get(6).setTranslateY(0);
            }
            case 6 -> {
                islands.getChildren().get(0).setTranslateX(-112);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(112);
                islands.getChildren().get(1).setTranslateY(-225);

                islands.getChildren().get(2).setTranslateX(225);
                islands.getChildren().get(2).setTranslateY(0);

                islands.getChildren().get(3).setTranslateX(112);
                islands.getChildren().get(3).setTranslateY(225);

                islands.getChildren().get(4).setTranslateX(-112);
                islands.getChildren().get(4).setTranslateY(225);

                islands.getChildren().get(5).setTranslateX(-225);
                islands.getChildren().get(5).setTranslateY(0);
            }
            case 5 -> {
                islands.getChildren().get(0).setTranslateX(0);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(225);
                islands.getChildren().get(1).setTranslateY(0);

                islands.getChildren().get(2).setTranslateX(112);
                islands.getChildren().get(2).setTranslateY(225);

                islands.getChildren().get(3).setTranslateX(-112);
                islands.getChildren().get(3).setTranslateY(225);

                islands.getChildren().get(4).setTranslateX(-225);
                islands.getChildren().get(4).setTranslateY(0);
            }
            case 4 -> {
                islands.getChildren().get(0).setTranslateX(-225);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(225);
                islands.getChildren().get(1).setTranslateY(-225);

                islands.getChildren().get(2).setTranslateX(225);
                islands.getChildren().get(2).setTranslateY(225);

                islands.getChildren().get(3).setTranslateX(-225);
                islands.getChildren().get(3).setTranslateY(225);
            }
            case 3 -> {
                islands.getChildren().get(0).setTranslateX(0);
                islands.getChildren().get(0).setTranslateY(-225);

                islands.getChildren().get(1).setTranslateX(225);
                islands.getChildren().get(1).setTranslateY(225);

                islands.getChildren().get(2).setTranslateX(-225);
                islands.getChildren().get(2).setTranslateY(225);
            }
        }
    }

    private void showClouds() {
        int playerNumber = App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNumber();
        for(int i=0; i<playerNumber; i++) {
            Group cloud = new Group();
            cloud.setId("cloud_"+i);
            ImageView imageView = new ImageView(new Image("game/graphics/board/cloud_"+i+".png"));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100);
            cloud.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            cloud.getChildren().add(imageView);
            switch (playerNumber) {
                case 2, 4 -> {
                    if(!gameState.getBoard().getClouds().get(i).getStudents().isEmpty())
                        for (int j = 0; j < 3; j++) {
                            ImageView student = new ImageView();
                            student.setPreserveRatio(true);
                            student.setFitHeight(45);
                            student.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            if (j == 0) {
                                student.setTranslateY(24);
                                student.setTranslateX(16);
                            }
                            if (j == 1) {
                                student.setTranslateY(17);
                                student.setTranslateX(39);
                            }
                            if (j == 2) {
                                student.setTranslateY(41);
                                student.setTranslateX(33);
                            }
                            Color studentColor = gameState.getBoard().getClouds().get(i).getStudents().get(j);
                            switch (studentColor) {
                                case RED -> student.setImage(new Image("game/graphics/students/Red_Student.png"));
                                case GREEN -> student.setImage(new Image("game/graphics/students/Green_Student.png"));
                                case BLUE -> student.setImage(new Image("game/graphics/students/Blue_Student.png"));
                                case YELLOW -> student.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                                case PINK -> student.setImage(new Image("game/graphics/students/Pink_Student.png"));
                            }
                            cloud.getChildren().add(student);
                        }
                }
                case 3 -> {
                    if(!gameState.getBoard().getClouds().get(i).getStudents().isEmpty())
                        for (int j = 0; j < 4; j++) {
                            ImageView student = new ImageView();
                            student.setPreserveRatio(true);
                            student.setFitHeight(45);
                            student.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            student.setTranslateX(16 + ((j % 2) * 23));
                            if (j == 0)
                                student.setTranslateY(19);
                            if (j == 1)
                                student.setTranslateY(19);
                            if (j == 2)
                                student.setTranslateY(38);
                            if (j == 3)
                                student.setTranslateY(38);
                            Color studentColor = gameState.getBoard().getClouds().get(i).getStudents().get(j);
                            switch (studentColor) {
                                case RED -> student.setImage(new Image("game/graphics/students/Red_Student.png"));
                                case GREEN -> student.setImage(new Image("game/graphics/students/Green_Student.png"));
                                case BLUE -> student.setImage(new Image("game/graphics/students/Blue_Student.png"));
                                case YELLOW -> student.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                                case PINK -> student.setImage(new Image("game/graphics/students/Pink_Student.png"));
                            }
                            cloud.getChildren().add(student);
                        }
                }
            }

            clouds.getChildren().add(cloud);
        }
        switch (playerNumber) {
            case 2 -> {
                clouds.getChildren().get(0).setTranslateX(50);
                clouds.getChildren().get(1).setTranslateX(-50);
            }
            case 3 -> {
                clouds.getChildren().get(0).setTranslateX(50);
                clouds.getChildren().get(1).setTranslateX(-50);
                clouds.getChildren().get(0).setTranslateY(-50);
                clouds.getChildren().get(1).setTranslateY(-50);
                clouds.getChildren().get(2).setTranslateY(50);
            }
            case 4 -> {
                clouds.getChildren().get(0).setTranslateX(50);
                clouds.getChildren().get(1).setTranslateX(-50);
                clouds.getChildren().get(0).setTranslateY(-50);
                clouds.getChildren().get(1).setTranslateY(-50);
                clouds.getChildren().get(2).setTranslateY(50);
                clouds.getChildren().get(2).setTranslateX(50);
                clouds.getChildren().get(3).setTranslateY(50);
                clouds.getChildren().get(3).setTranslateX(-50);
            }
        }
    }

    private void showDeck() {
        List<AssistantCard> deck = new ArrayList<>();
        deck.addAll(gameState.getPlayerById(App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId()).getDeck());
        zone.setFill(javafx.scene.paint.Color.TRANSPARENT);
        zone.setHeight(150);
        zone.setWidth(60*deck.size());
        zone.setTranslateX(40);
        for(int i=0; i<=deck.size(); i++){
            if(i<deck.size()) {
                ImageView imageView = new ImageView(new Image("game/graphics/animals/assistant_" + deck.get(i).getSpeedValue() + ".png"));
                imageView.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
                imageView.setId("assistant_"+deck.get(i).getSpeedValue());
                imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                imageView.setY(0);
                imageView.setTranslateX(0);
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(150);
                this.deck.getChildren().add(imageView);
            }
            else {
                ImageView imageView = new ImageView(new Image("game/graphics/animals/backs/back_" + App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId() + ".png"));
                imageView.setTranslateX(0);
                imageView.setY(0);
                imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(150);
                imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        backEntered();
                        ((ImageView) mouseEvent.getSource()).removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
                    }
                });
                this.deck.getChildren().add(imageView);
            }
        }
    }

    private void showAssistantCardsPlayed() {
        Map<Integer, AssistantCard> assistantCards = new HashMap<>();
        assistantCards.putAll(gameState.getCardsPlayed());
        for(int i : assistantCards.keySet()) {
            ImageView imageView = new ImageView(new Image("game/graphics/animals/assistant_" + assistantCards.get(i).getSpeedValue() + ".png"));
            imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            imageView.setY(0);
            imageView.setTranslateX(0);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(150);
            Label player = new Label();
            String playerName = App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNames().get(i);
            player.setText(playerName);
            player.setTextFill(javafx.scene.paint.Color.WHITE);
            player.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            player.setFont(new Font("Snap ITC", 20));
            int playerNumber = App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNumber();
            switch (i) {
                case 0 -> player.setTranslateY(-32);
                case 1 -> {
                    imageView.setTranslateX(150);
                    player.setTranslateY(-32);
                    player.setTranslateX(150);
                }
                case 2 -> {
                    if(playerNumber==3) {
                        imageView.setTranslateX(75);
                        player.setTranslateX(75);
                    }
                    player.setTranslateY(168);
                    imageView.setTranslateY(200);
                }
                case 3 -> {
                    imageView.setTranslateX(150);
                    imageView.setTranslateY(200);
                    player.setTranslateY(168);
                    player.setTranslateX(150);
                }
            }
            this.assistantCardsPlayed.getChildren().add(player);
            this.assistantCardsPlayed.getChildren().add(imageView);
        }
    }

    private void showSchool() {
        int playerNumber = App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNumber();
        for(int i=0; i<playerNumber; i++){
            ImageView imageView = new ImageView(new Image("game/graphics/animals/backs/back_"+i+".png"));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(50);
            imageView.setTranslateX(70*i);
            imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            imageView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                imageView.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
            });

            imageView.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            });
            schoolHeader.getChildren().add(imageView);

            Label player = new Label();
            String playerName = App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNames().get(i);
            if(playerName.length()>8)
                playerName = playerName.substring(0, 7);
            player.setText(playerName);
            player.setTextFill(javafx.scene.paint.Color.WHITE);
            player.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            player.setFont(new Font("Snap ITC", 13));
            player.setTranslateX(-13 +(70*i));
            player.setTranslateY(60);
            schoolHeader.getChildren().add(player);

            Group schoolGroup = new Group();
            schoolGroup.setId("school_"+i);
            schoolGroup.setVisible(false);
            school.getChildren().add(schoolGroup);
            createPlayerSchool(i, school.getChildren().indexOf(schoolGroup));

            int id = App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId();
            if (id == i) {
                schoolGroup.setVisible(true);
                mySchoolIndex = school.getChildren().indexOf(schoolGroup);
            }

            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                for(int c=1; c<school.getChildren().size(); c++)
                    school.getChildren().get(c).setVisible(false);
                schoolGroup.setVisible(true);
            });
        }
    }

    private void backEntered() {

        RotateTransition rotateTransition1 = new RotateTransition(Duration.millis(50),((ImageView) deck.getChildren().get(deck.getChildren().size()-1)));
        rotateTransition1.setAxis(Rotate.Y_AXIS);
        rotateTransition1.setByAngle(90);
        rotateTransition1.play();

        Timeline vanishBack = new Timeline(
                new KeyFrame(Duration.millis(50), event -> ((ImageView) deck.getChildren().get(deck.getChildren().size()-1)).setVisible(false)));
        vanishBack.setDelay(Duration.millis(50));
        vanishBack.play();

        for(int i=0; i<deck.getChildren().size()-1; i++) {
            ((ImageView) deck.getChildren().get(i)).getTransforms().add(new Rotate(180, Rotate.Y_AXIS));
            RotateTransition rotateTransition2 = new RotateTransition(Duration.millis(50), ((ImageView) deck.getChildren().get(i)));
            rotateTransition2.setAxis(Rotate.Y_AXIS);
            rotateTransition2.setByAngle(90);
            rotateTransition2.setDelay(Duration.millis(50));
            rotateTransition2.play();
        }

        eventHandlersEntered = new HashMap<>();
        eventHandlersExited = new HashMap<>();
        for(int i=0; i<deck.getChildren().size()-1; i++){
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    new KeyValue(((ImageView) deck.getChildren().get(i)).translateXProperty(), 50*i)));
            timeline.setAutoReverse(false);
            int finalI1 = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    event -> ((ImageView) deck.getChildren().get(finalI1)).setTranslateX(50*finalI1)));
            timeline.setDelay(Duration.millis(150));
            timeline.play();

            int finalI = i;
            Timeline activeDeck = new Timeline(new KeyFrame(Duration.millis(100), event -> {
                deck.getChildren().get(finalI).addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        AssistantCardEntered((ImageView) mouseEvent.getSource());
                        eventHandlersEntered.put(finalI, this);
                    }
                });
                deck.getChildren().get(finalI).addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        AssistantCardExited((ImageView) mouseEvent.getSource());
                        eventHandlersExited.put(finalI, this);
                    }
                });
            }));

            activeDeck.setDelay(Duration.millis(250));
            activeDeck.play();

        }
        Timeline closeDeck = new Timeline(new KeyFrame(Duration.millis(100), event ->
        {
            deckZone.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                backExited();
                ((Group) mouseEvent.getSource()).removeEventHandler(MouseEvent.MOUSE_EXITED, this);
            }
        });
        }
        ));
        closeDeck.setDelay(Duration.millis(50));
        closeDeck.play();
    }

    private void backExited() {
        for(int i=0; i<deck.getChildren().size()-1; i++){
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    new KeyValue(((ImageView) deck.getChildren().get(i)).translateXProperty(), 0)));
            timeline.setAutoReverse(false);
            int finalI1 = i;
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    event -> ((ImageView) deck.getChildren().get(finalI1)).setTranslateX(0)));
            timeline.play();

            RotateTransition rotateTransition2 = new RotateTransition(Duration.millis(50), ((ImageView) deck.getChildren().get(i)));
            rotateTransition2.setAxis(Rotate.Y_AXIS);
            rotateTransition2.setByAngle(90);
            rotateTransition2.setDelay(Duration.millis(100));
            rotateTransition2.play();
        }

        Timeline vanishBack = new Timeline(
                new KeyFrame(Duration.millis(50), event -> ((ImageView) deck.getChildren().get(deck.getChildren().size()-1)).setVisible(true)));
        vanishBack.setDelay(Duration.millis(150));
        vanishBack.play();

        RotateTransition rotateTransition1 = new RotateTransition(Duration.millis(50),((ImageView) deck.getChildren().get(deck.getChildren().size()-1)));
        rotateTransition1.setAxis(Rotate.Y_AXIS);
        rotateTransition1.setByAngle(270);
        rotateTransition1.setDelay(Duration.millis(150));
        rotateTransition1.play();

        Timeline openDeck = new Timeline(new KeyFrame(Duration.millis(100), event ->
        {
            this.deck.getChildren().get(this.deck.getChildren().size()-1).addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    backEntered();
                    ((ImageView) mouseEvent.getSource()).removeEventHandler(MouseEvent.MOUSE_ENTERED, this);
                }
            });
        }
        ));
        openDeck.setDelay(Duration.millis(250));
        openDeck.play();
    }

    private void AssistantCardEntered(ImageView card) {
        int index = deck.getChildren().indexOf(card);
        ((ImageView) deck.getChildren().get(index)).setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
        ((ImageView) deck.getChildren().get(index)).setFitHeight(180);
        for(int i=index+1; i<deck.getChildren().size()-1; i++) {
            /*Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    new KeyValue(((ImageView) deck.getChildren().get(i)).translateXProperty(), 50*i+117)));
            timeline.setAutoReverse(false);
            timeline.play();*/
            deck.getChildren().get(i).setTranslateX(50*i+83);
        }
        for(int i=0; i<deck.getChildren().size()-1; i++)
            if(i != index)
                ((ImageView) deck.getChildren().get(i)).setY(30);
    }

    private void AssistantCardExited(ImageView card) {
        int index = deck.getChildren().indexOf(card);
        ((ImageView) deck.getChildren().get(index)).setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
        ((ImageView) deck.getChildren().get(index)).setFitHeight(150);
        for(int i=index+1; i<deck.getChildren().size()-1; i++) {
            /*Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    new KeyValue(((ImageView) deck.getChildren().get(i)).translateXProperty(), 50*i)));
            timeline.setAutoReverse(false);
            timeline.play();*/
            deck.getChildren().get(i).setTranslateX(50*i);
        }
        for(int i=0; i<deck.getChildren().size()-1; i++)
            if(i != index)
                ((ImageView) deck.getChildren().get(i)).setY(0);
    }

    private void createPlayerSchool(int id, int index) {
        //Tower portion
        Group towerZone = new Group();
        int towers = gameState.getPlayerById(id).getSchool().getTowers();
        for (int i = 0; i < towers; i++) {
            ImageView tower = new ImageView();
            tower.setPreserveRatio(true);
            tower.setFitHeight(38);
            tower.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            if (i < 4)
                tower.setTranslateY(-125);
            else
                tower.setTranslateY(-81);
            tower.setTranslateX(225 + (30*(i%4)));
            TowerColor color = App.getCurrentApp().client.getServerObserver().getInfo().getTeams().get(id);
            switch (color) {
                case BLACK -> tower.setImage(new Image("game/graphics/towers/Black_Tower.png"));
                case WHITE -> tower.setImage(new Image("game/graphics/towers/White_Tower.png"));
                case GREY -> tower.setImage(new Image("game/graphics/towers/Grey_Tower.png"));
            }
            towerZone.getChildren().add(tower);
        }
        ((Group) school.getChildren().get(index)).getChildren().add(towerZone);

        //Professor portion
        Group professorZone = new Group();
        Map<Color, Boolean> professorMap = new HashMap<>();
        professorMap.putAll(gameState.getPlayerById(id).getSchool().getProfessors());

        for (Color c : professorMap.keySet()) {
            ImageView professor = new ImageView();
            professor.setPreserveRatio(true);
            professor.setFitHeight(40);
            professor.setTranslateY(-18);
            professor.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 8), 10, 0.5, 0.0, 0.0);");
            if (c == Color.BLUE) {
                if (professorMap.get(c)) {
                    professor.setTranslateX(350);
                    professor.setImage(new Image("game/graphics/professors/Blue_Professor.png"));
                }
            }
            if (c == Color.PINK) {
                if (professorMap.get(Color.PINK)) {
                    professor.setTranslateX(309);
                    professor.setImage(new Image("game/graphics/professors/Pink_Professor.png"));
                }
            }
            if (c == Color.YELLOW) {
                if (professorMap.get(Color.YELLOW)) {
                    professor.setTranslateX(269);
                    professor.setImage(new Image("game/graphics/professors/Yellow_Professor.png"));
                }
            }
            if (c == Color.RED) {
                if (professorMap.get(Color.RED)) {
                    professor.setTranslateX(228);
                    professor.setImage(new Image("game/graphics/professors/Red_Professor.png"));
                }
            }
            if (c == Color.GREEN) {
                if (professorMap.get(Color.GREEN)) {
                    professor.setTranslateX(186);
                    professor.setImage(new Image("game/graphics/professors/Green_Professor.png"));
                }
            }
            professorZone.getChildren().add(professor);
        }
        ((Group) school.getChildren().get(index)).getChildren().add(professorZone);

        //Dining room portion
        Group diningRoomZone = new Group();
        Map<Color, Integer> diningRoomMap = new HashMap<>();
        diningRoomMap.putAll(gameState.getPlayerById(id).getSchool().getDiningRoom());

        for (Color c : diningRoomMap.keySet()) {
            for (int i = 0; i < diningRoomMap.get(c); i++){
                ImageView studentd = new ImageView();
                studentd.setPreserveRatio(true);
                studentd.setFitHeight(45);
                studentd.setTranslateY(280 - (27.2 * i));
                studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                switch (c) {
                    case GREEN -> {
                        studentd.setTranslateX(184);
                        studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                    }
                    case RED -> {
                        studentd.setTranslateX(225);
                        studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                    }
                    case YELLOW -> {
                        studentd.setTranslateX(266);
                        studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                    }
                    case PINK -> {
                        studentd.setTranslateX(307);
                        studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                    }
                    case BLUE -> {
                        studentd.setTranslateX(348);
                        studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                    }
                }
                diningRoomZone.getChildren().add(studentd);
            }
        }
        ((Group) school.getChildren().get(index)).getChildren().add(diningRoomZone);

        //Entrance portion
        Group entranceZone = new Group();
        List<Color> entrance = new ArrayList<>();
        entrance.addAll(gameState.getPlayerById(id).getSchool().getEntrance());

        for (int i = 0; i < entrance.size(); i++) {
            ImageView student = new ImageView();
            student.setPreserveRatio(true);
            student.setFitHeight(45);
            student.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");

            if (gameState.getPlayers().size() == 2 || gameState.getPlayers().size() == 4) {
                if (i < 4)
                    student.setTranslateY(335);
                else
                    student.setTranslateY(368);
                if (i < 4)
                    student.setTranslateX(230 + (30 * (i)));
                else
                    student.setTranslateX(260 + (30 * (i % 4)));
            }
            if (gameState.getPlayers().size() == 3) {
                if (i < 5)
                    student.setTranslateY(335);
                else
                    student.setTranslateY(368);
                if (i < 5)
                    student.setTranslateX(230 + (30 * (i)));
                else
                    student.setTranslateX(260 + (30 * (i % 5)));
            }

            Color studentColor = entrance.get(i);
            switch (studentColor) {
                case RED -> student.setImage(new Image("game/graphics/students/Red_Student.png"));
                case GREEN -> student.setImage(new Image("game/graphics/students/Green_Student.png"));
                case BLUE -> student.setImage(new Image("game/graphics/students/Blue_Student.png"));
                case YELLOW -> student.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                case PINK -> student.setImage(new Image("game/graphics/students/Pink_Student.png"));
            }
            entranceZone.getChildren().add(student);
        }
        ((Group) school.getChildren().get(index)).getChildren().add(entranceZone);
    }

}
