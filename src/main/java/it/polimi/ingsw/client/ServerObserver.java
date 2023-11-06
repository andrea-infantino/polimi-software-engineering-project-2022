package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.App;
import it.polimi.ingsw.client.gui.game.ImageLoader;
import it.polimi.ingsw.client.views.*;
import it.polimi.ingsw.resources.ActionPhase1Move;
import it.polimi.ingsw.resources.GameState;
import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.enumerators.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerObserver {
    private final Client client;
    private PlayerInfo info;
    public boolean card4played;
    public AtomicBoolean mustRefresh, alreadyPlayedCharacterCard;
    public GamePhase gamePhase;
    public GameState gameState;
    public int characterCardResponse;
    public int currentPlayerId;
    public List<String> messages;
    private ImageLoader imageLoader;

    public List<Integer> winners;

    public ServerObserver (Client client) {
        this.client = client;
        info = new PlayerInfo();
    }


    public PlayerInfo getInfo() {
        return info;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    //Messages from the server
    public void playersAndMode(Message message) {
        info.setPlayerNames((Map<Integer, String>) message.getParam1());
        info.setExpertMode((Boolean) message.getParam2());
        if(!Client.isGuiMode)
            client.getCurrentView().response = message;
        else
            if(info.getPlayerNumber() == 4)
                client.loadingScreenSwitch = ScreenSwitch.JOIN_TEAM;
    }

    public void startGameSession(Message message) {
        info.setTeams((Map<Integer, TowerColor>) message.getParam1());
        alreadyPlayedCharacterCard = new AtomicBoolean(false);
        if(!Client.isGuiMode)
            client.changeView(new BoardView((GameState) message.getParam2()));
        else {
            imageLoader = new ImageLoader();
            messages = new ArrayList<>();
            this.gameState = (GameState) message.getParam2();
            client.loadingScreenSwitch = ScreenSwitch.BOARD;
            mustRefresh = new AtomicBoolean(false);
        }
    }

    public void notYourTurn() {
        View.writeLine("  You can't perform actions when it's not your turn");
        if(Client.isGuiMode)
            messages.add("Can't do it now!");
        characterCardResponse = -1;
    }

    public void unplayableAssistantCard() {
        View.writeLine("  You can't play that assistant card");
        if(Client.isGuiMode)
            messages.add("Can't play that card!");
    }

    public void modelChanged(Message message) {
        if(!Client.isGuiMode) {
            ((BoardView) client.getCurrentView()).setGameState(((GameState) message.getParam1()));
            ((BoardView) client.getCurrentView()).refresh();
        }
        else
        {
            this.gameState = (GameState) message.getParam1();
            mustRefresh.set(true);
        }
    }

    public void newTurn(Message message) {
        currentPlayerId = (int) message.getParam1();
        card4played = false;
        alreadyPlayedCharacterCard.set(false);
        if((int) message.getParam1() == info.getPlayerId()) {
            View.writeLine("  It's your turn...");
            if(Client.isGuiMode)
                messages.add("It's your turn!");
        }
        else {
            View.writeLine("  It's " + info.getPlayerNames().get(((int) message.getParam1())) + "'s turn...");
            if(Client.isGuiMode)
                messages.add("It's " + info.getPlayerNames().get(((int) message.getParam1())) + "'s turn!");
        }
    }

    public void startPlanningPhase() {
        View.writeLine("  New round begun;\n       Starting planning phase...");
        if(Client.isGuiMode)
            messages.add("Starting planning phase!");
        gamePhase = GamePhase.PLANNING_PHASE;
    }

    public void startActionPhase1() {
        View.writeLine("  Starting move students phase...");
        if(Client.isGuiMode)
            messages.add("Starting move students phase!");
        gamePhase = GamePhase.ACTION_PHASE1;
    }

    public void startActionPhase2() {
        View.writeLine("  Starting move mother nature phase...");
        if(Client.isGuiMode)
            messages.add("Starting move mother nature phase!");
        gamePhase = GamePhase.ACTION_PHASE2;
    }

    public void startActionPhase3() {
        View.writeLine("  Selecting cloud phase...");
        if(Client.isGuiMode)
            messages.add("Starting selecting cloud phase!");
        gamePhase = GamePhase.ACTION_PHASE3;
    }

    public void endGame(Message message) {
        TowerColor winner = (TowerColor) message.getParam1();
        client.getServerHandler().stop();
        if(!Client.isGuiMode) {
            client.getCurrentView().stop();
            View.clearAll();
            if (winner == info.getTeams().get(info.getPlayerId()))
                View.writeLine("\n\n\n                VICTORY!");
            else {
                View.writeLine("\n\n\n                DEFEAT!\n\n");
                if (info.getPlayerNumber() == 4) {
                    View.writeLine("        The winners are:");
                    for (int id : info.getPlayerNames().keySet())
                        if (info.getTeams().get(id) == winner)
                            View.writeLine("            - " + info.getPlayerNames().get(id));
                } else {
                    for (int id : info.getPlayerNames().keySet())
                        if (info.getTeams().get(id) == winner)
                            View.writeLine("        The winner is " + info.getPlayerNames().get(id));
                }

            }
            View.writeLine("\n\n     Press enter to quit...");
            Scanner input = new Scanner(System.in);
            input.nextLine();
        }
        else{
            winners = new ArrayList<>();
            for (int id : info.getPlayerNames().keySet())
                if (info.getTeams().get(id) == winner)
                    winners.add(id);
            gamePhase = GamePhase.END_GAME;
            }
        client.terminate();
    }

    //Response messages
    public void adminClient(){
        if(!Client.isGuiMode)
            client.changeView(new GameSetup());
        else {
            client.loadingScreenSwitch = ScreenSwitch.GAME_CREATION;
        }
    }

    public void guestClient(){
        if(!Client.isGuiMode)
            client.changeView(new AddToGame());
        else
            client.loadingScreenSwitch = ScreenSwitch.ADD_PLAYER;
    }

    public void ack() {
        if(!Client.isGuiMode)
            client.getCurrentView().response = new Message(MessageCode.ACK, null, null);
        else
            client.preLoadingScreenSwitch = ScreenSwitch.ACK;

    }

    public void gameCreated() {
        if(!Client.isGuiMode)
            client.getCurrentView().response = new Message(MessageCode.GAME_CREATED, null, null);
        else
            client.loadingScreenSwitch = ScreenSwitch.ADD_PLAYER;
    }

    public void creatingGame() {
        if(!Client.isGuiMode)
            client.getCurrentView().response = new Message(MessageCode.CREATING_GAME, null, null);
        else
            client.preLoadingScreenSwitch = ScreenSwitch.CREATING_GAME;
    }

    public void maxPlayerNumberReached() {
        if(!Client.isGuiMode)
            client.getCurrentView().response = new Message(MessageCode.MAX_PLAYER_NUMBER_REACHED, null, null);
        else
            client.preLoadingScreenSwitch = ScreenSwitch.SERVER_FULL;
    }

    public void playerAdded(Message message) {
        info.setPlayerId((Integer) message.getParam1());
        info.setPlayerNumber((Integer)message.getParam2());
        if(!Client.isGuiMode)
            client.getCurrentView().response = message;
        else
            client.preLoadingScreenSwitch = ScreenSwitch.PLAYER_ADDED;
    }

    public void fullTeam() {
        if(!Client.isGuiMode)
            client.getCurrentView().response = new Message(MessageCode.FULL_TEAM, null, null);
        else
            client.preLoadingScreenSwitch = ScreenSwitch.TEAM_FULL;
    }

    public void invalidMove() {
        View.writeLine("Invalid move! You can't do this!");
        if(Client.isGuiMode)
            messages.add("Invalid move!");
    }

    public void notEnoughCoins() {
        View.writeLine("Not enough coins!");
        if(Client.isGuiMode)
            messages.add("Not enough coins!");
        characterCardResponse = -1;
    }

    public void alreadyPlayedACharacterCard(){
        View.writeLine("You have already played a character card in this turn!");
        if(Client.isGuiMode)
            messages.add("Can't do it now!");
        characterCardResponse = -1;
    }

    public void waitForCharacterCardParameters(){
        characterCardResponse = 1;
    }

    public void playerUsedCharacterCard(int playerId, int characterCardNumber){
        if (gameState.getCharacterCards()[characterCardNumber].getId() == 3)
            card4played = true;
        if(playerId == getInfo().getPlayerId()) {
            View.writeLine("You have just activated the character card number " + ((int) characterCardNumber + 1));
            if(Client.isGuiMode)
                messages.add("card activated!");
        }
        else {
            View.writeLine(getInfo().getPlayerNames().get(playerId) + " has just activated the character card number " + ((int) characterCardNumber + 1));
            if(Client.isGuiMode)
                messages.add(getInfo().getPlayerNames().get(playerId) + " has activated a character card!");
        }
    }

    //messages to Server
    public void playAssistantCard(int speedValue) {
        client.getServerHandler().sendMessage(new Message(MessageCode.PLAY_ASSISTANT_CARD, getInfo().getPlayerId(), speedValue));
    }

    public void moveStudents(List<ActionPhase1Move> moves) {
        client.getServerHandler().sendMessage(new Message(MessageCode.MOVE_STUDENTS, getInfo().getPlayerId(), moves));
    }

    public void moveMotherNature(int islandId) {
        client.getServerHandler().sendMessage(new Message(MessageCode.MOVE_MOTHER_NATURE, getInfo().getPlayerId(), islandId));
    }

    public void chooseCloud(int cloudId) {
        client.getServerHandler().sendMessage(new Message(MessageCode.CHOOSE_CLOUD_TILE, getInfo().getPlayerId(), cloudId));
    }

    public void playCharacterCard(int characterCardNumber) {
        client.getServerHandler().sendMessage((new Message(MessageCode.PLAY_A_CHARACTER_CARD, getInfo().getPlayerId(), characterCardNumber)));
        characterCardResponse = 0;
    }

    public void activateCharacterCardEffect (Object o_1, Object o_2){
        client.getServerHandler().sendMessage((new Message(MessageCode.ACTIVATE_CHARACTER_CARD_EFFECT, o_1, o_2)));
    }



}
