package it.polimi.ingsw.client.views;

import it.polimi.ingsw.client.CommandHandler;
import it.polimi.ingsw.client.cli.*;
import it.polimi.ingsw.resources.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BoardView extends View{

    private GameState gameState;
    private AtomicBoolean mustRefresh;
    private AtomicBoolean endGame;

    public BoardView(GameState gameState) {
        mustRefresh = new AtomicBoolean(false);
        endGame = new AtomicBoolean(false);
        this.gameState = gameState;
    }

    @Override
    public void run() {
        CommandHandler commandHandler = new CommandHandler(this);
        Thread commandThread = new Thread(commandHandler, "Eryantis - Command Handler");
        commandThread.start();

        while(!endGame.get()) {
            clearAll();
            if(!getClient().getServerObserver().getInfo().isExpertMode())
                printNormalGameBoard();
            else
                printExpertGameBoard();

            mustRefresh.set(false);
            writeLine(" Command Line:\n");
            while(!mustRefresh.get()) {write("");}
        }
        commandHandler.stop();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public synchronized void refresh() {
        mustRefresh.set(true);
    }

    public GameState getGameState() { return gameState; }

    public void endGame(){
        endGame.set(true);
    }

    /*private void printLogs() {
        synchronized (getClient().getServerObserver().logs) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("     Logs:\n");
            for (String log : getClient().getServerObserver().logs) {
                stringBuilder.append("     " + log + "\n");
                getClient().getServerObserver().logs.remove(log);
            }
            stringBuilder.append("\n-------------------------------------------------------------------------------------------------------------------\n");
            View.writeLine(stringBuilder.toString());
        }
    }*/

    private void printNormalGameBoard() {
        NormalBoard board = new NormalBoard();
        board.myId = getClient().getServerObserver().getInfo().getPlayerId();
        board.playerNumber = super.getClient().getServerObserver().getInfo().getPlayerNumber();
        board.islands = new ArrayList<>();
        for(GameState.Board.Island i : gameState.getBoard().getIslands()) {
            Island island = new Island();
            island.id = i.getId();
            island.islandNumber = i.getIslandNumber();
            island.controlledBy = i.getControlledBy();
            island.hasMotherNature = i.isHasMotherNature();
            island.hasNoEntryTile = i.isHasNoEntryTile();
            island.students = new ArrayList<>();
            island.students.addAll(i.getStudents());
            board.islands.add(island);
        }

        board.clouds = new ArrayList<>();
        board.schools = new ArrayList<>();
        for(int id=0; id<board.playerNumber; id++) {
            Cloud cloud = new Cloud();
            cloud.id = id;
            cloud.playerNumber = board.playerNumber;
            cloud.students = new ArrayList<>();
            for(GameState.Board.Cloud c : gameState.getBoard().getClouds())
                if (c.getId() == id)
                    cloud.students.addAll(c.getStudents());
            board.clouds.add(cloud);

            School school = new School();
            school.team = getClient().getServerObserver().getInfo().getTeams().get(id);
            school.playerName = getClient().getServerObserver().getInfo().getPlayerNames().get(id);
            school.playerId = id;
            school.entrance = new ArrayList<>();
            school.diningRoom = new HashMap<>();
            school.professors = new HashMap<>();
            if(id == super.getClient().getServerObserver().getInfo().getPlayerId())
                school.yours = true;
            for(GameState.Player p : gameState.getPlayers())
                if(p.getId() == id) {
                    school.entrance.addAll(p.getSchool().getEntrance());
                    school.towers = p.getSchool().getTowers();
                    school.diningRoom.putAll(p.getSchool().getDiningRoom());
                    school.professors.putAll(p.getSchool().getProfessors());
                }
            board.schools.add(school);
        }

        board.cardsPlayed = new ArrayList<>();
        for(int id : gameState.getCardsPlayed().keySet()){
            AssistantCard assistantCard = new AssistantCard();
            assistantCard.moves = gameState.getCardsPlayed().get(id).getMoveValue();
            assistantCard.speed = gameState.getCardsPlayed().get(id).getSpeedValue();
            assistantCard.playerName = getClient().getServerObserver().getInfo().getPlayerNames().get(id);
            board.cardsPlayed.add(assistantCard);
        }

        board.deck = new ArrayList<>();
        for(GameState.Player player : gameState.getPlayers())
            if(player.getId() == getClient().getServerObserver().getInfo().getPlayerId())
                for(it.polimi.ingsw.server.model.AssistantCard a : player.getDeck()) {
                    AssistantCard assistantCard = new AssistantCard();
                    assistantCard.moves = a.getMoveValue();
                    assistantCard.speed = a.getSpeedValue();
                    assistantCard.playerName = getClient().getServerObserver().getInfo().getPlayerNames().get(player.getId());
                    board.deck.add(assistantCard);
                }

        System.out.println(board);
    }

    private void printExpertGameBoard() {
        ExpertBoard board = new ExpertBoard();
        board.myId = getClient().getServerObserver().getInfo().getPlayerId();
        board.playerNumber = super.getClient().getServerObserver().getInfo().getPlayerNumber();
        board.islands = new ArrayList<>();
        for(GameState.Board.Island i : gameState.getBoard().getIslands()) {
            Island island = new Island();
            island.id = i.getId();
            island.islandNumber = i.getIslandNumber();
            island.controlledBy = i.getControlledBy();
            island.hasMotherNature = i.isHasMotherNature();
            island.hasNoEntryTile = i.isHasNoEntryTile();
            island.students = new ArrayList<>();
            island.students.addAll(i.getStudents());
            board.islands.add(island);
        }

        board.clouds = new ArrayList<>();
        board.schools = new ArrayList<>();
        for(int id=0; id<board.playerNumber; id++) {
            Cloud cloud = new Cloud();
            cloud.id = id;
            cloud.playerNumber = board.playerNumber;
            cloud.students = new ArrayList<>();
            for(GameState.Board.Cloud c : gameState.getBoard().getClouds())
                if (c.getId() == id)
                    cloud.students.addAll(c.getStudents());
            board.clouds.add(cloud);

            School school = new School();
            school.team = getClient().getServerObserver().getInfo().getTeams().get(id);
            school.playerName = getClient().getServerObserver().getInfo().getPlayerNames().get(id);
            school.playerId = id;
            school.entrance = new ArrayList<>();
            school.diningRoom = new HashMap<>();
            school.professors = new HashMap<>();
            if(id == super.getClient().getServerObserver().getInfo().getPlayerId())
                school.yours = true;
            for(GameState.Player p : gameState.getPlayers())
                if(p.getId() == id) {
                    school.entrance.addAll(p.getSchool().getEntrance());
                    school.towers = p.getSchool().getTowers();
                    school.diningRoom.putAll(p.getSchool().getDiningRoom());
                    school.professors.putAll(p.getSchool().getProfessors());
                }
            board.schools.add(school);
        }

        board.cardsPlayed = new ArrayList<>();
        for(int id : gameState.getCardsPlayed().keySet()){
            AssistantCard assistantCard = new AssistantCard();
            assistantCard.moves = gameState.getCardsPlayed().get(id).getMoveValue();
            assistantCard.speed = gameState.getCardsPlayed().get(id).getSpeedValue();
            assistantCard.playerName = getClient().getServerObserver().getInfo().getPlayerNames().get(id);
            board.cardsPlayed.add(assistantCard);
        }

        board.deck = new ArrayList<>();
        for(GameState.Player player : gameState.getPlayers())
            if(player.getId() == getClient().getServerObserver().getInfo().getPlayerId())
                for(it.polimi.ingsw.server.model.AssistantCard a : player.getDeck()) {
                    AssistantCard assistantCard = new AssistantCard();
                    assistantCard.moves = a.getMoveValue();
                    assistantCard.speed = a.getSpeedValue();
                    assistantCard.playerName = getClient().getServerObserver().getInfo().getPlayerNames().get(player.getId());
                    board.deck.add(assistantCard);
                }

        board.playerCoins = new HashMap<>();
        for(GameState.Player player : gameState.getPlayers())
            board.playerCoins.put(player.getId(), player.getCoins());

        board.characterCards = new CharacterCard[3];
        for(int i=0; i<3; i++) {
            CharacterCard characterCard = new CharacterCard();
            characterCard.number = i;
            characterCard.id = gameState.getCharacterCards()[i].getId();
            characterCard.noEntryTiles = gameState.getCharacterCards()[i].getRemainingNoEntryTiles();
            characterCard.cost = gameState.getCharacterCards()[i].getCost();
            characterCard.students = new ArrayList<>(gameState.getCharacterCards()[i].getStudents());
            board.characterCards[i] = characterCard;
        }

        System.out.println(board);
    }

}
