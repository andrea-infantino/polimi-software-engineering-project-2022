package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.resources.ActionPhase1Move;
import it.polimi.ingsw.resources.GameState;
import it.polimi.ingsw.resources.Message;
import it.polimi.ingsw.resources.enumerators.TowerColor;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.states.ChooseCloudTile;
import it.polimi.ingsw.server.controller.states.MoveMotherNature;
import it.polimi.ingsw.server.controller.states.NextRoundOrEndGame;
import it.polimi.ingsw.server.model.Cloud;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.PlayerTurn;
import it.polimi.ingsw.server.model.Team;
import it.polimi.ingsw.server.model.expert.ExpertGame;
import it.polimi.ingsw.server.model.expert.ExpertPlayer;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static it.polimi.ingsw.resources.enumerators.MessageCode.*;

/**
 * class that observe the view
 */
public class ViewObserver {
    private GameController gameController;
    private int counter;
    private boolean canAddPlayer;
    private CharacterCardHandler characterCardHandler;
    private Map<Integer, Socket> playersSockets;
    private List<ClientHandler> clients;


    public ViewObserver () {
        clients = new ArrayList<>();
        playersSockets = new HashMap<>();
        gameController = new GameController();
        counter = 0;
        canAddPlayer = false;
    }
    /**
     *
     * @param expertMode true if the game is in expert mode, false otherwise
     * @param playerNumber number of players in the game
     * @return an acknowledgement message
     */
    public Message expertModeAndPlayerNumber(boolean expertMode, int playerNumber) {
        gameController.changeModel(expertMode, playerNumber);
        gameController.nextState();
        canAddPlayer = true;
        return new Message(GAME_CREATED, null, null);
    }

    /**
     *
     * @param playerName
     * @param client
     * @return
     */
    public Message addNewPlayer(String playerName, ClientHandler client) {
        if(canAddPlayer) {
            if (Server.game.getPlayerNumber() > Server.game.getPlayers().size()) {
                gameController.changeModel(playerName, counter);
                playersSockets.put(counter, client.getClient());
                clients.add(client);
                try {
                    client.sendResponseMessage(new Message(PLAYER_ADDED, counter, Server.game.getPlayerNumber()));
                } catch (IOException e) { e.printStackTrace(); }
                counter++;
                if (Server.game.getPlayerNumber() == counter) {
                    gameController.nextState();
                    counter = 0;
                    Map<Integer, String> playerNames = new HashMap<>();
                    for(Player p : Server.game.getPlayers())
                        playerNames.put(p.getId(), p.getName());
                    try {
                        for (ClientHandler c : clients) {
                            c.sendResponseMessage(new Message(PLAYERS_AND_MODE, playerNames, Server.game.isExpertMode()));
                        }
                    } catch (IOException e) { e.printStackTrace(); }

                    if (Server.game.getPlayerNumber() != 4) {
                        gameController.changeModel(null, null);
                        System.out.println("Initialized game");
                        gameController.nextState();
                        gameController.changeModel(null, null);
                        System.out.println("Refilled cloud tiles");
                        gameController.nextState();
                        Map<Integer, TowerColor> playersAssociation = new HashMap<>();
                        for (Player p : Server.game.getPlayers())
                            playersAssociation.put(p.getId(), Server.game.getTeamById(p.getId()).towerColor);
                        try {
                            for (ClientHandler c : clients) {
                                c.sendResponseMessage(new Message(START_GAME_SESSION, playersAssociation, new GameState(Server.game)));
                                c.sendResponseMessage(new Message(START_PLANNING_PHASE, null, null));
                                c.sendResponseMessage(new Message(NEW_TURN, Server.game.currentRound.getOrder().get(0), null));
                            }
                        } catch (IOException e) { e.printStackTrace(); }
                    }
                }
                return new Message(ACK, null, null);
            }
            return new Message(MAX_PLAYER_NUMBER_REACHED, null, null);
        }
        return new Message(CREATING_GAME, null, null);
    }

    /**
     *
     * @param playerId
     * @param teamId
     * @return
     */
    public Message addPlayerInTeam(int playerId, int teamId) {
        if(Server.game.getTeamById(teamId).getPlayers().size() < 2) {
            gameController.changeModel(playerId, teamId);
            counter++;
            if(counter == 4) {
                gameController.nextState();
                counter = 0;
                gameController.changeModel(null, null);
                gameController.nextState();
                gameController.changeModel(null, null);
                gameController.nextState();
                Map<Integer, TowerColor> teamsAssociation = new HashMap<>();
                for (Team t : Server.game.getTeams())
                    for (int id : t.getPlayers())
                        teamsAssociation.put(id, t.towerColor);
                try {
                    for (ClientHandler c : clients) {
                        c.sendResponseMessage(new Message(START_GAME_SESSION, teamsAssociation, new GameState(Server.game)));
                        c.sendResponseMessage(new Message(START_PLANNING_PHASE, null, null));
                        c.sendResponseMessage(new Message(NEW_TURN, Server.game.currentRound.getOrder().get(0), null));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Message(ACK, null, null);
        }
        return new Message(FULL_TEAM, null, null);
    }

    /**
     *
     * @param playerId
     * @param assistantCardSpeedValue
     * @return
     */
    public Message playAssistantCard(int playerId, int assistantCardSpeedValue) {
        if(Server.game.currentRound.getOrder().get(counter) == playerId) {
            if(Server.game.currentRound.playableCards(Server.game.getPlayerById(playerId)).contains(assistantCardSpeedValue)) {
                gameController.changeModel(playerId, assistantCardSpeedValue);
                try {
                    for (ClientHandler c : clients) {
                        c.sendResponseMessage(new Message(MODEL_CHANGED, new GameState(Server.game), null));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                counter++;
                if(counter < Server.game.getPlayerNumber())
                    try {
                        for (ClientHandler c : clients) {
                            c.sendResponseMessage(new Message(NEW_TURN, Server.game.currentRound.getOrder().get(counter), null));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            else
                return new Message(UNPLAYABLE_ASSISTANT_CARD, null, null);
            if(counter == Server.game.getPlayerNumber()) {
                counter = 0;
                gameController.nextState();
                gameController.changeModel(null, null);
                System.out.println("Establish round order\n" + Server.game.currentRound.getAssistantCardsPlayed());
                gameController.nextState();
                Server.game.currentRound.currentTurn = new PlayerTurn(Server.game.currentRound.getOrder().get(counter));
                try {
                    for (ClientHandler c : clients) {
                        c.sendResponseMessage(new Message(START_ACTION_PHASE_1, null, null));
                        c.sendResponseMessage(new Message(NEW_TURN, Server.game.currentRound.currentTurn.getPlayerId(), null));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return new Message(ACK, null, null);
        }
        return new Message(NOT_YOUR_TURN, null, null);
    }

    /**
     *
     * @param playerId
     * @param moves
     * @return
     */
    public Message moveStudents(int playerId, List<ActionPhase1Move> moves) {
        System.out.println("Students movement\n" + Server.game.currentRound.getAssistantCardsPlayed());
        if(Server.game.currentRound.currentTurn.getPlayerId() == playerId) {
            gameController.changeModel(playerId, moves);
            try {
                for (ClientHandler c : clients) {
                    c.sendResponseMessage(new Message(MODEL_CHANGED, new GameState(Server.game), null));
                    c.sendResponseMessage(new Message(START_ACTION_PHASE_2, null, null));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameController.nextState();
            return new Message(ACK, null, null);
        }
        return new Message(NOT_YOUR_TURN, null, null);
    }

    /**
     *
     * @param playerId
     * @param islandId
     * @return
     */
    public Message moveMotherNature(int playerId, int islandId) {
        if(Server.game.currentRound.currentTurn.getPlayerId() == playerId) {
            int maxPossibleMoves = Server.game.currentRound.getAssistantCardsPlayed().get(playerId).getMoveValue();
            if(Server.game.currentRound.currentTurn.card4Played)
                maxPossibleMoves += 2;
            int islandCounter = Server.game.getBoard().getMotherNatureIsland().getId();
            for (int i = 0; i < maxPossibleMoves; i++) {
                if (islandCounter < Server.game.getBoard().getIslandGroups().size() - 1)
                    islandCounter++;
                else
                    islandCounter = 0;

                if(islandCounter == islandId) {
                    System.out.println("Teams:" + Server.game.getTeamById(0).towerColor + "   " + Server.game.getTeamById(1).towerColor);
                    gameController.changeModel(islandId, null);
                    try {
                        for (ClientHandler c : clients) {
                            c.sendResponseMessage(new Message(MODEL_CHANGED, new GameState(Server.game), null));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!((MoveMotherNature) gameController.getState()).checkEndConditions()) {
                        gameController.nextState();
                        try {
                            for (ClientHandler c : clients) {
                                c.sendResponseMessage(new Message(START_ACTION_PHASE_3, null, null));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        gameController.nextState();
                        gameController.changeModel(null, null);
                        try {
                            for (ClientHandler c : clients) {
                                c.sendResponseMessage(new Message(END_GAME, Server.game.getTeamById(Server.game.winnerTeamId).towerColor, null));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return new Message(ACK, null, null);
                }
            }
            return new Message(INVALID_MOVE, null, null);
        }
        return new Message(NOT_YOUR_TURN, null, null);
    }

    /**
     *
     * @param playerId
     * @param cloudId
     * @return
     */
    public Message chooseCloudTile(int playerId, int cloudId) {
        if(Server.game.currentRound.currentTurn.getPlayerId() == playerId) {
            gameController.changeModel(playerId, cloudId);
            try {
                for (ClientHandler c : clients) {
                    if(counter == Server.game.getPlayerNumber()-1)
                        Server.game.currentRound.resetCardsPlayed();
                    c.sendResponseMessage(new Message(MODEL_CHANGED, new GameState(Server.game), null));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            counter++;
            if(counter < Server.game.getPlayerNumber()) {
                gameController.nextState();
                Server.game.currentRound.currentTurn = new PlayerTurn(Server.game.currentRound.getOrder().get(counter));
                try {
                    for (ClientHandler c : clients) {
                        c.sendResponseMessage(new Message(START_ACTION_PHASE_1, null, null));
                        c.sendResponseMessage(new Message(NEW_TURN, Server.game.currentRound.currentTurn.getPlayerId(), null));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                ((ChooseCloudTile) gameController.getState()).mustEndRound = true;
                gameController.nextState();
                if(!((NextRoundOrEndGame) gameController.getState()).checkEndConditions()) {
                    counter = 0;
                    gameController.nextState();
                    gameController.changeModel(null, null);
                    try {
                        for (ClientHandler c : clients) {
                            c.sendResponseMessage(new Message(MODEL_CHANGED, new GameState(Server.game), null));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    gameController.nextState();
                    try {
                        for (ClientHandler c : clients) {
                            c.sendResponseMessage(new Message(START_PLANNING_PHASE, null, null));
                            c.sendResponseMessage(new Message(NEW_TURN, Server.game.currentRound.getOrder().get(0), null));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    gameController.nextState();
                    gameController.changeModel(null, null);
                    try {
                        for (ClientHandler c : clients) {
                            c.sendResponseMessage(new Message(END_GAME, Server.game.winnerTeamId, null));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return new Message(ACK, null, null);
        }
        return new Message(NOT_YOUR_TURN, null, null);
    }

    /**
     * message that represents the player's will to play a card
     * @param playerId the identifier of the player
     * @param characterCardNumber the number of the card
     * @return in case of errors returns NOT_YOUR_TURN or NOT_ENOUGH_COINS message. Otherwise, if the card's effect need some parameters, return the message WAIT_FOR_CHARACTER_CARD_PARAMETERS, else if this is not che case activates the effect.
     */
    public Message playACharacterCard(int playerId, int characterCardNumber) {
        if (Server.game.currentRound.currentTurn.getPlayerId() == playerId){
            if (((ExpertPlayer) Server.game.getPlayerById(playerId)).getCoins() >= ((ExpertGame) Server.game).getCharacterCards()[characterCardNumber].getCost()) {
                if(!Server.game.currentRound.currentTurn.hasAlreadyPlayedACharacterCard) {
                    Server.game.currentRound.currentTurn.hasAlreadyPlayedACharacterCard = true;
                    ((ExpertGame) Server.game).playCharacterCard(((ExpertPlayer) Server.game.getPlayerById(playerId)), characterCardNumber);
                    try {
                        for (ClientHandler c : clients) {
                            c.sendResponseMessage(new Message(PLAYER_USED_CHARACTER_CARD, playerId, characterCardNumber));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    characterCardHandler = new CharacterCardHandler(characterCardNumber, playerId);
                    if (characterCardHandler.getCharacterCardId() == 1 || characterCardHandler.getCharacterCardId() == 3 || characterCardHandler.getCharacterCardId() == 5 || characterCardHandler.getCharacterCardId() == 7) {
                        return activateCharacterCardEffect(null, null);
                    } else
                        return new Message(WAIT_FOR_CHARACTER_CARD_PARAMETERS, null, null);
                }
                return new Message(ALREADY_PLAYED_A_CHARACTER_CARD, null, null);
            }
            return new Message(NOT_ENOUGH_COINS, null, null);
        }
        return new Message(NOT_YOUR_TURN, null, null);
    }

    /**
     * message that activate the effect of a character card
     * @param o_1 generic object that will be cast
     * @param o_2 generic object that will be cast
     * @return an acknowledgement message
     */
    public Message activateCharacterCardEffect(Object o_1, Object o_2) {
        characterCardHandler.activateEffect(o_1, o_2);
        try {
            for (ClientHandler c : clients) {
                c.sendResponseMessage(new Message(MODEL_CHANGED, new GameState(Server.game), null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Message(ACK, null, null);
    }

}
