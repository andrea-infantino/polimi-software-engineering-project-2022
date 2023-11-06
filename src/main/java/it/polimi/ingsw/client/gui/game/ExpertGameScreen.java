package it.polimi.ingsw.client.gui.game;

import it.polimi.ingsw.client.gui.App;
import it.polimi.ingsw.resources.GameState;
import it.polimi.ingsw.resources.enumerators.GamePhase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ExpertGameScreen extends NormalGameScreen{
    @FXML
    ImageView character_card_0, character_card_1, character_card_2;
    @FXML
    Label card_cost_0, card_cost_1, card_cost_2;
    @FXML
    Group coins, characterCardsZone;

    private boolean canClick;

    @Override
    public void initialize(){
        canClick = true;
        super.initialize();
        Platform.runLater(() -> {
            showCharacterCards();
            showCoins();
        });
    }

    private void showCoins(){
        for(int i=0; i< App.getCurrentApp().client.getServerObserver().getInfo().getPlayerNumber(); i++){
            Label coin = new Label();
            coin.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            coin.setTextFill(Color.WHITE);
            coin.setFont(new Font("Snap ITC", 15));
            coin.setText("" + gameState.getPlayers().get(i).getCoins());
            if(i != App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId())
                coin.setVisible(false);
            coin.setTranslateX(15);
            coin.setTranslateY(15);
            coins.getChildren().add(coin);
            schoolHeader.getChildren().get(i*2).addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                for(int k=1; k<coins.getChildren().size(); k++)
                    coins.getChildren().get(k).setVisible(false);
                coin.setVisible(true);
            });
        }
    }
    private void showCharacterCards(){
        character_card_0.setImage(new Image(setImage(super.gameState.getCharacterCards()[0].getId())));
        character_card_1.setImage(new Image(setImage(super.gameState.getCharacterCards()[1].getId())));
        character_card_2.setImage(new Image(setImage(super.gameState.getCharacterCards()[2].getId())));
        card_cost_0.setText("" + super.gameState.getCharacterCards()[0].getCost());
        card_cost_1.setText("" + super.gameState.getCharacterCards()[1].getCost());
        card_cost_2.setText("" + super.gameState.getCharacterCards()[2].getCost());
        if((App.getCurrentApp().client.getServerObserver().gamePhase == GamePhase.ACTION_PHASE1 ||
                App.getCurrentApp().client.getServerObserver().gamePhase == GamePhase.ACTION_PHASE2 ||
                App.getCurrentApp().client.getServerObserver().gamePhase == GamePhase.ACTION_PHASE3)
                && App.getCurrentApp().client.getServerObserver().currentPlayerId == App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId()) {
            character_card_0.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                if (canClick)
                    character_card_0.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
            });
            character_card_0.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                if (canClick)
                    character_card_0.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            });
            character_card_1.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                if (canClick)
                    character_card_1.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
            });
            character_card_1.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                if (canClick)
                    character_card_1.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            });
            character_card_2.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                if (canClick)
                    character_card_2.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
            });
            character_card_2.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                if (canClick)
                    character_card_2.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            });
            character_card_0.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (canClick) {
                    int playerCoins = gameState.getPlayerById(App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId()).getCoins();
                    if (!App.getCurrentApp().client.getServerObserver().alreadyPlayedCharacterCard.get()) {
                        if (playerCoins >= gameState.getCharacterCards()[0].getCost()) {
                            if(gameState.getCharacterCards()[0].getId() != 9) {
                                App.getCurrentApp().client.getServerObserver().playCharacterCard(0);
                                handleMouseClicked(gameState.getCharacterCards()[0].getId());
                                character_card_0.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            }
                            else {
                                if(((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(2)).getChildren().isEmpty())
                                    App.getCurrentApp().client.getServerObserver().messages.add("Can't use with an empty hall!");
                                else {
                                    App.getCurrentApp().client.getServerObserver().playCharacterCard(0);
                                    handleMouseClicked(gameState.getCharacterCards()[0].getId());
                                    character_card_0.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                                }
                            }
                        }
                        else
                            App.getCurrentApp().client.getServerObserver().notEnoughCoins();
                    } else
                        App.getCurrentApp().client.getServerObserver().alreadyPlayedACharacterCard();
                }
            });
            character_card_1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (canClick) {
                    int playerCoins = gameState.getPlayerById(App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId()).getCoins();
                    if (!App.getCurrentApp().client.getServerObserver().alreadyPlayedCharacterCard.get()) {
                        if (playerCoins >= gameState.getCharacterCards()[1].getCost()) {
                            if(gameState.getCharacterCards()[1].getId() != 9) {
                                character_card_1.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                                App.getCurrentApp().client.getServerObserver().playCharacterCard(1);
                                handleMouseClicked(gameState.getCharacterCards()[1].getId());
                            }
                            else {
                                if(((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(2)).getChildren().isEmpty())
                                    App.getCurrentApp().client.getServerObserver().messages.add("Can't use with an empty hall!");
                                else {
                                    App.getCurrentApp().client.getServerObserver().playCharacterCard(1);
                                    handleMouseClicked(gameState.getCharacterCards()[1].getId());
                                    character_card_1.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                                }
                            }
                        }
                        else
                            App.getCurrentApp().client.getServerObserver().notEnoughCoins();
                    } else
                        App.getCurrentApp().client.getServerObserver().alreadyPlayedACharacterCard();
                }
            });
            character_card_2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (canClick) {
                    int playerCoins = gameState.getPlayerById(App.getCurrentApp().client.getServerObserver().getInfo().getPlayerId()).getCoins();
                    if (!App.getCurrentApp().client.getServerObserver().alreadyPlayedCharacterCard.get()) {
                        if (playerCoins >= gameState.getCharacterCards()[2].getCost()) {
                            if(gameState.getCharacterCards()[2].getId() != 9) {
                                character_card_2.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                                App.getCurrentApp().client.getServerObserver().playCharacterCard(2);
                                handleMouseClicked(gameState.getCharacterCards()[2].getId());
                            }
                            else {
                                if(((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(2)).getChildren().isEmpty())
                                    App.getCurrentApp().client.getServerObserver().messages.add("Can't use with an empty hall!");
                                else {
                                    App.getCurrentApp().client.getServerObserver().playCharacterCard(2);
                                    handleMouseClicked(gameState.getCharacterCards()[2].getId());
                                    character_card_2.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                                }
                            }
                        }
                        else
                            App.getCurrentApp().client.getServerObserver().notEnoughCoins();
                    } else
                        App.getCurrentApp().client.getServerObserver().alreadyPlayedACharacterCard();
                }
            });
        }
        initializeCards();
    }

    private void handleMouseClicked(int id){
        canClick = false;
        canContinue.set(false);
        App.getCurrentApp().client.getServerObserver().alreadyPlayedCharacterCard.set(true);
        switch (id) {
            case 0 -> {
                AtomicBoolean flag = new AtomicBoolean(true);
                for(Node n : characterCardsZone.getChildren())
                    if(n.getId() == "students_card_0")
                        for(Node s : ((Group) n).getChildren()){
                            s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            s.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                                if(flag.get())
                                    s.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                                if(flag.get())
                                    s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                if(flag.get()) {
                                    flag.set(false);
                                    for (Node st : ((Group) n).getChildren())
                                        st.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                                    s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                                    for (Node island : islands.getChildren()) {
                                        island.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                                        island.addEventHandler(MouseEvent.MOUSE_ENTERED, event1 -> {
                                            island.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                                        });
                                        island.addEventHandler(MouseEvent.MOUSE_EXITED, event1 -> {
                                            island.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                                        });
                                        island.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
                                            it.polimi.ingsw.resources.enumerators.Color student = null;
                                            switch (((ImageView) s).getImage().getUrl().substring(((ImageView) s).getImage().getUrl().length()-18)) {
                                                case "ts/Red_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.RED;
                                                case "s/Blue_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.BLUE;
                                                case "/Green_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.GREEN;
                                                case "Yellow_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.YELLOW;
                                                case "s/Pink_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.PINK;
                                            }
                                            App.getCurrentApp().client.getServerObserver().activateCharacterCardEffect(student, Integer.parseInt(island.getId().substring(7)));
                                            canContinue.set(true);
                                        });
                                    }
                                }
                            });
                        }
            }
            case 2, 4 -> {
                for (Node island : islands.getChildren()) {
                    island.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                    island.addEventHandler(MouseEvent.MOUSE_ENTERED, event1 -> {
                        island.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                    });
                    island.addEventHandler(MouseEvent.MOUSE_EXITED, event1 -> {
                        island.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                    });
                    island.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
                        App.getCurrentApp().client.getServerObserver().activateCharacterCardEffect(Integer.parseInt(island.getId().substring(7)), null);
                        canContinue.set(true);
                    });
                }
            }
            case 6 -> {
                AtomicBoolean flag = new AtomicBoolean(true);
                AtomicInteger count = new AtomicInteger(0);
                AtomicInteger check = new AtomicInteger(0);
                List<it.polimi.ingsw.resources.enumerators.Color> students = new ArrayList<>();
                List<it.polimi.ingsw.resources.enumerators.Color> entrance = new ArrayList<>();
                for(Node n : characterCardsZone.getChildren())
                    if(n.getId() == "students_card_6")
                        for(Node s : ((Group) n).getChildren()){
                            s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            s.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                                if(flag.get() && count.get() < 3)
                                    s.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                                if(flag.get() && count.get() < 3)
                                    s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                if(flag.get() && count.get() < 3) {
                                    count.getAndIncrement();
                                    s.setVisible(false);
                                    switch (((ImageView) s).getImage().getUrl().substring(((ImageView) s).getImage().getUrl().length()-18)) {
                                        case "ts/Red_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.RED);
                                        case "s/Blue_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.BLUE);
                                        case "/Green_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.GREEN);
                                        case "Yellow_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.YELLOW);
                                        case "s/Pink_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.PINK);
                                    }
                                    if(count.get() == 3)
                                        for(Node st : ((Group) n).getChildren())
                                            st.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                                    for (Node student : ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren())
                                        student.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                                }
                            });
                        }
                for (Node s : ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren()) {
                    s.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                        if(count.get() > 0)
                            s.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                    });
                    s.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                        if(count.get() > 0)
                            s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                    });
                    s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(count.get() > 0){
                            flag.set(false);
                            check.getAndIncrement();
                            s.setVisible(false);
                            for(Node n : characterCardsZone.getChildren())
                                if(n.getId() == "students_card_6")
                                    for(Node st : ((Group) n).getChildren())
                                        st.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            switch (((ImageView) s).getImage().getUrl().substring(((ImageView) s).getImage().getUrl().length()-18)) {
                                case "ts/Red_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.RED);
                                case "s/Blue_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.BLUE);
                                case "/Green_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.GREEN);
                                case "Yellow_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.YELLOW);
                                case "s/Pink_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.PINK);
                            }
                            if(check.get() == count.get()) {
                                App.getCurrentApp().client.getServerObserver().activateCharacterCardEffect(students, entrance);
                                canContinue.set(true);
                            }
                        }
                    });
                }
            }
            case 8 -> {
                for(Node n : characterCardsZone.getChildren())
                    if(n.getId() == "students_card_8")
                        for(Node s : ((Group) n).getChildren()) {
                            s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            s.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                                s.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                                s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                it.polimi.ingsw.resources.enumerators.Color student = null;
                                switch (((ImageView) s).getImage().getUrl().substring(((ImageView) s).getImage().getUrl().length()-18)) {
                                    case "ts/Red_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.RED;
                                    case "s/Blue_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.BLUE;
                                    case "/Green_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.GREEN;
                                    case "Yellow_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.YELLOW;
                                    case "s/Pink_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.PINK;
                                }
                                App.getCurrentApp().client.getServerObserver().activateCharacterCardEffect(student, null);
                                canContinue.set(true);
                            });
                        }
            }
            case 10 -> {
                for(Node n : characterCardsZone.getChildren())
                    if(n.getId() == "students_card_10")
                        for(Node s : ((Group) n).getChildren()) {
                            s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            s.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                                s.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                                s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                it.polimi.ingsw.resources.enumerators.Color student = null;
                                switch (((ImageView) s).getImage().getUrl().substring(((ImageView) s).getImage().getUrl().length()-18)) {
                                    case "ts/Red_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.RED;
                                    case "s/Blue_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.BLUE;
                                    case "/Green_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.GREEN;
                                    case "Yellow_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.YELLOW;
                                    case "s/Pink_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.PINK;
                                }
                                App.getCurrentApp().client.getServerObserver().activateCharacterCardEffect(student, null);
                                canContinue.set(true);
                            });
                        }
            }
            case 11 -> {
                for(Node n : characterCardsZone.getChildren())
                    if(n.getId() == "students_card_11")
                        for(Node s : ((Group) n).getChildren()) {
                            s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            s.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                                s.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                                s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                            });
                            s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                                it.polimi.ingsw.resources.enumerators.Color student = null;
                                switch (((ImageView) s).getImage().getUrl().substring(((ImageView) s).getImage().getUrl().length()-18)) {
                                    case "ts/Red_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.RED;
                                    case "s/Blue_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.BLUE;
                                    case "/Green_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.GREEN;
                                    case "Yellow_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.YELLOW;
                                    case "s/Pink_Student.png" -> student = it.polimi.ingsw.resources.enumerators.Color.PINK;
                                }
                                App.getCurrentApp().client.getServerObserver().activateCharacterCardEffect(student, null);
                                canContinue.set(true);
                            });
                        }
            }
            case 9 -> {
                AtomicBoolean flag = new AtomicBoolean(true);
                AtomicInteger count = new AtomicInteger(0);
                AtomicInteger check = new AtomicInteger(0);
                List<it.polimi.ingsw.resources.enumerators.Color> students = new ArrayList<>();
                List<it.polimi.ingsw.resources.enumerators.Color> entrance = new ArrayList<>();
                diningRoom.setVisible(false);
                for (Node s : ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(2)).getChildren()) {
                    s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                    s.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                        if(flag.get() && count.get() < 2)
                            s.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                    });
                    s.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                        if(flag.get() && count.get() < 2)
                            s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                    });
                    s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(flag.get() && count.get() < 2) {
                            count.getAndIncrement();
                            s.setVisible(false);
                            switch (((ImageView) s).getImage().getUrl().substring(((ImageView) s).getImage().getUrl().length()-18)) {
                                case "ts/Red_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.RED);
                                case "s/Blue_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.BLUE);
                                case "/Green_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.GREEN);
                                case "Yellow_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.YELLOW);
                                case "s/Pink_Student.png" -> students.add(it.polimi.ingsw.resources.enumerators.Color.PINK);
                            }
                            if(count.get() == 2)
                                for(Node st : ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(2)).getChildren())
                                    st.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            for (Node student : ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren())
                                student.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                        }
                    });
                }

                for (Node s : ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(3)).getChildren()) {
                    s.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                        if(count.get() > 0)
                            s.setStyle("-fx-effect: dropshadow(gaussian, powderblue, 10, 0.6, 0.0, 0.0);");
                    });
                    s.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                        if(count.get() > 0)
                            s.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.6, 0.0, 0.0);");
                    });
                    s.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if(count.get() > 0){
                            flag.set(false);
                            check.getAndIncrement();
                            s.setVisible(false);
                            for(Node st : ((Group) ((Group) school.getChildren().get(mySchoolIndex)).getChildren().get(2)).getChildren())
                                st.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
                            switch (((ImageView) s).getImage().getUrl().substring(((ImageView) s).getImage().getUrl().length()-18)) {
                                case "ts/Red_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.RED);
                                case "s/Blue_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.BLUE);
                                case "/Green_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.GREEN);
                                case "Yellow_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.YELLOW);
                                case "s/Pink_Student.png" -> entrance.add(it.polimi.ingsw.resources.enumerators.Color.PINK);
                            }
                            if(check.get() == count.get()) {
                                diningRoom.setVisible(true);
                                App.getCurrentApp().client.getServerObserver().activateCharacterCardEffect(entrance, students);
                                canContinue.set(true);
                            }
                        }
                    });
                }
            }
            default -> canContinue.set(true);
        }

    }

    private void initializeCards() {
        switch (gameState.getCharacterCards()[0].getId()){
            case 0 -> {
                Group students = new Group();
                students.setId("students_card_0");
                for(int i=0; i<4; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    studentd.setTranslateY(45);
                    studentd.setTranslateX(30+ 20*i);
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[0].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 10 -> {
                Group students = new Group();
                students.setId("students_card_10");
                for(int i=0; i<4; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    studentd.setTranslateY(45);
                    studentd.setTranslateX(30+ 20*i);
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[0].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 4 -> initializeNoEntryTiles();
            case 6 -> {
                Group students = new Group();
                students.setId("students_card_6");
                for(int i=0; i<gameState.getCharacterCards()[0].getStudents().size(); i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0, 3 -> studentd.setTranslateX(40);
                        case 1, 4 -> studentd.setTranslateX(60);
                        case 2, 5 -> studentd.setTranslateX(80);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[0].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 8 -> {
                Group students = new Group();
                students.setId("students_card_8");
                for(int i=0; i<5; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0 -> studentd.setTranslateX(30);
                        case 1 -> studentd.setTranslateX(55);
                        case 2 -> studentd.setTranslateX(80);
                        case 3 -> studentd.setTranslateX(42);
                        case 4 -> studentd.setTranslateX(67);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (i) {
                        case 0 -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case 1 -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case 2 -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case 3 -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case 4 -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 11 -> {
                Group students = new Group();
                students.setId("students_card_11");
                for(int i=0; i<5; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0 -> studentd.setTranslateX(30);
                        case 1 -> studentd.setTranslateX(55);
                        case 2 -> studentd.setTranslateX(80);
                        case 3 -> studentd.setTranslateX(42);
                        case 4 -> studentd.setTranslateX(67);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (i) {
                        case 0 -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case 1 -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case 2 -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case 3 -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case 4 -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
        }
        switch (gameState.getCharacterCards()[1].getId()){
            case 0 -> {
                Group students = new Group();
                students.setId("students_card_0");
                for(int i=0; i<4; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    studentd.setTranslateY(45);
                    studentd.setTranslateX(170 + 30+ 20*i);
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[1].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 10 -> {
                Group students = new Group();
                students.setId("students_card_10");
                for(int i=0; i<4; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    studentd.setTranslateY(45);
                    studentd.setTranslateX(170 + 30+ 20*i);
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[1].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 4 -> initializeNoEntryTiles();
            case 6 -> {
                Group students = new Group();
                students.setId("students_card_6");
                for(int i=0; i<gameState.getCharacterCards()[1].getStudents().size(); i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0, 3 -> studentd.setTranslateX(170+40);
                        case 1, 4 -> studentd.setTranslateX(170+60);
                        case 2, 5 -> studentd.setTranslateX(170+80);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[1].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 8 -> {
                Group students = new Group();
                students.setId("students_card_8");
                for(int i=0; i<5; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0 -> studentd.setTranslateX(170+30);
                        case 1 -> studentd.setTranslateX(170+55);
                        case 2 -> studentd.setTranslateX(170+80);
                        case 3 -> studentd.setTranslateX(170+42);
                        case 4 -> studentd.setTranslateX(170+67);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (i) {
                        case 0 -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case 1 -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case 2 -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case 3 -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case 4 -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 11 -> {
                Group students = new Group();
                students.setId("students_card_11");
                for(int i=0; i<5; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0 -> studentd.setTranslateX(170+30);
                        case 1 -> studentd.setTranslateX(170+55);
                        case 2 -> studentd.setTranslateX(170+80);
                        case 3 -> studentd.setTranslateX(170+42);
                        case 4 -> studentd.setTranslateX(170+67);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (i) {
                        case 0 -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case 1 -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case 2 -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case 3 -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case 4 -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
        }
        switch (gameState.getCharacterCards()[2].getId()){
            case 0 -> {
                Group students = new Group();
                students.setId("students_card_0");
                for(int i=0; i<4; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    studentd.setTranslateY(45);
                    studentd.setTranslateX(340 + 30+ 20*i);
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[2].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 10 -> {
                Group students = new Group();
                students.setId("students_card_10");
                for(int i=0; i<4; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    studentd.setTranslateY(45);
                    studentd.setTranslateX(340 + 30+ 20*i);
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[2].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 4 -> initializeNoEntryTiles();
            case 6 -> {
                Group students = new Group();
                students.setId("students_card_6");
                for(int i=0; i<gameState.getCharacterCards()[2].getStudents().size(); i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0, 3 -> studentd.setTranslateX(340+40);
                        case 1, 4 -> studentd.setTranslateX(340+60);
                        case 2, 5 -> studentd.setTranslateX(340+80);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (gameState.getCharacterCards()[2].getStudents().get(i)) {
                        case GREEN -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case RED -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case YELLOW -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case PINK -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case BLUE -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 8 -> {
                Group students = new Group();
                students.setId("students_card_8");
                for(int i=0; i<5; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0 -> studentd.setTranslateX(340+30);
                        case 1 -> studentd.setTranslateX(340+55);
                        case 2 -> studentd.setTranslateX(340+80);
                        case 3 -> studentd.setTranslateX(340+42);
                        case 4 -> studentd.setTranslateX(340+67);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (i) {
                        case 0 -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case 1 -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case 2 -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case 3 -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case 4 -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
            case 11 -> {
                Group students = new Group();
                students.setId("students_card_11");
                for(int i=0; i<5; i++){
                    ImageView studentd = new ImageView();
                    studentd.setPreserveRatio(true);
                    studentd.setFitHeight(45);
                    if(i<3)
                        studentd.setTranslateY(45);
                    else
                        studentd.setTranslateY(65);
                    switch (i) {
                        case 0 -> studentd.setTranslateX(340+30);
                        case 1 -> studentd.setTranslateX(340+55);
                        case 2 -> studentd.setTranslateX(340+80);
                        case 3 -> studentd.setTranslateX(340+42);
                        case 4 -> studentd.setTranslateX(340+67);
                    }
                    studentd.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 10), 10, 0.5, 0.0, 0.0);");
                    switch (i) {
                        case 0 -> {
                            studentd.setImage(new Image("game/graphics/students/Green_Student.png"));
                        }
                        case 1 -> {
                            studentd.setImage(new Image("game/graphics/students/Red_Student.png"));
                        }
                        case 2 -> {
                            studentd.setImage(new Image("game/graphics/students/Yellow_Student.png"));
                        }
                        case 3 -> {
                            studentd.setImage(new Image("game/graphics/students/Pink_Student.png"));
                        }
                        case 4 -> {
                            studentd.setImage(new Image("game/graphics/students/Blue_Student.png"));
                        }
                    }
                    students.getChildren().add(studentd);
                }
                characterCardsZone.getChildren().add(students);
            }
        }
    }

    private void initializeNoEntryTiles() {
        for(int i =0; i<islands.getChildren().size(); i++) {
            ImageView noEntryTile = new ImageView(new Image("game/graphics/board/noEntryTile.png"));
            noEntryTile.setId("noEntryTile_"+islands.getChildren().get(i).getId().substring(7));
            noEntryTile.setPreserveRatio(true);
            noEntryTile.setFitHeight(30);
            noEntryTile.setTranslateY(0);
            noEntryTile.setTranslateX(77);
            noEntryTile.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0.0, 0.0);");
            ((Group) islands.getChildren().get(i)).getChildren().add(noEntryTile);
            noEntryTile.setVisible(false);
            if(gameState.getBoard().getIslands().get(i).isHasNoEntryTile())
                noEntryTile.setVisible(true);
        }
        /*for(GameState.Board.Island i : gameState.getBoard().getIslands())
            for(Node island : islands.getChildren())
                if(i.getId() == Integer.parseInt(island.getId().substring(7)) && !i.isHasNoEntryTile())
                    for(Node n : ((Group) island).getChildren())
                        if(n.getId() == "noEntryTile_"+i.getId())
                            n.setVisible(false);*/
    }

    private String setImage(int i){
        switch (i){
            case 0 -> {
                return "game/graphics/characterCards/Monk.jpg";
            }
            case 1 -> {
                return "game/graphics/characterCards/Farmer.jpg";
            }
            case 2 -> {
                return "game/graphics/characterCards/Herald.jpg";
            }
            case 3 -> {
                return "game/graphics/characterCards/Postman.jpg";
            }
            case 4 -> {
                return "game/graphics/characterCards/Grandmother.jpg";
            }
            case 5 -> {
                return "game/graphics/characterCards/Centaur.jpg";
            }
            case 6 -> {
                return "game/graphics/characterCards/Jester.jpg";
            }
            case 7 -> {
                return "game/graphics/characterCards/Knight.jpg";
            }
            case 8 -> {
                return "game/graphics/characterCards/Mushroom-Man.jpg";
            }
            case 9 -> {
                return "game/graphics/characterCards/Minstrel.jpg";
            }
            case 10 -> {
                return "game/graphics/characterCards/Princess.jpg";
            }
            case 11 -> {
                return "game/graphics/characterCards/Thief.jpg";
            }
        }
        return null;
    }
}

