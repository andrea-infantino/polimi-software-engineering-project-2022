package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.states.MoveMotherNature;
import it.polimi.ingsw.server.controller.states.MoveStudents;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.expert.ExpertGame;
import it.polimi.ingsw.server.model.expert.ExpertPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represents the character card handler
 */
public class CharacterCardHandler {
    private int characterCardNumber;
    private int playerId;
    private int characterCardId;

    /**
     * constructor
     * @param characterCardNumber number of the character card (from 0 to 2)
     * @param playerId identifier of the player who plays the card
     */
    public CharacterCardHandler (int characterCardNumber, int playerId) {
        this.characterCardNumber = characterCardNumber;
        this.characterCardId = ((ExpertGame) Server.game).getCharacterCards()[characterCardNumber].getId();
        this.playerId = playerId;
    }

    /**
     * request for the character card id
     * @return the character card id
     */
    public int getCharacterCardId() {
        return characterCardId;
    }

    /**
     * the activation of an effect
     * @param o_1 generic object that will be casted
     * @param o_2 generic object that will be casted
     * @return the effect's effect
     */
    public void activateEffect(Object o_1, Object o_2) {
        switch (characterCardId) {
            case 0: {
                Color student = (Color) o_1;
                int islandId = (Integer) o_2;
                ((ExpertGame) Server.game).getCharacterCards()[this.characterCardNumber].removeStudent(student);
                Server.game.getBoard().getIslandGroupById(islandId).addStudent(student);
                ((ExpertGame) Server.game).getCharacterCards()[this.characterCardNumber].addStudent(Server.game.getBoard().getBag().extract());
                break;
            }
            case 1: {
                Server.game.currentRound.currentTurn.card2Played = true;
                break;
            }
            case 2: {
                int islandId = (Integer) o_1;
                MoveMotherNature.islandConquer(islandId);
                break;
            }
            case 3: {
                Server.game.currentRound.currentTurn.card4Played = true;
                break;
            }
            case 4: {
                int islandId = (Integer) o_1;
                Server.game.getBoard().getIslandGroupById(islandId).hasNoEntryTile = true;
                ((ExpertGame) Server.game).getCharacterCards()[characterCardNumber].remainingNoEntryTiles--;
                break;
            }
            case 5: {
                Server.game.currentRound.currentTurn.card6Played = true;
                break;
            }
            case 6: {
                List<Color> toPutInSchool = (ArrayList<Color>) o_1;
                List<Color> toPutOnCard = (ArrayList<Color>) o_2;
                ((ExpertGame) Server.game).getCharacterCards()[characterCardNumber].removeStudents(toPutInSchool);
                Server.game.getPlayerById(playerId).getSchool().removeStudents(toPutOnCard);
                ((ExpertGame) Server.game).getCharacterCards()[characterCardNumber].addStudents(toPutOnCard);
                Server.game.getPlayerById(playerId).getSchool().addStudents(toPutInSchool);
                break;
            }
            case 7: {
                Server.game.currentRound.currentTurn.card8Played = true;
                break;
            }
            case 8: {
                Server.game.currentRound.currentTurn.card9Played = (Color) o_1;
                break;
            }
            case 9: {
                List<Color> toPutInDiningRoom = (ArrayList<Color>) o_1;
                List<Color> toPutInEntrance = (ArrayList<Color>) o_2;
                for (Color s : toPutInDiningRoom) {
                    Server.game.getPlayerById(playerId).getSchool().addStudentInDiningRoom(s);
                    if(Server.game.getPlayerById(playerId).getSchool().needCoin(s))
                        ((ExpertPlayer)Server.game.getPlayerById(playerId)).addCoin();
                    MoveStudents.moveProfessor(s, playerId);
                }
                Server.game.getPlayerById(playerId).getSchool().removeStudents(toPutInDiningRoom);
                Server.game.getPlayerById(playerId).getSchool().removeStudentsInDiningRoom(toPutInEntrance);
                Server.game.getPlayerById(playerId).getSchool().addStudents(toPutInEntrance);
                break;
            }
            case 10: {
                Color toPutInDiningRoom = (Color) o_1;
                ((ExpertGame) Server.game).getCharacterCards()[characterCardNumber].removeStudent(toPutInDiningRoom);
                ((ExpertGame) Server.game).getCharacterCards()[characterCardNumber].addStudent(Server.game.getBoard().getBag().extract());
                Server.game.getPlayerById(playerId).getSchool().addStudentInDiningRoom(toPutInDiningRoom);
                if(Server.game.getPlayerById(playerId).getSchool().needCoin(toPutInDiningRoom))
                    ((ExpertPlayer)Server.game.getPlayerById(playerId)).addCoin();
                MoveStudents.moveProfessor(toPutInDiningRoom, playerId);
                break;
            }
            case 11: {
                Color color = (Color) o_1;
                for(Player p : Server.game.getPlayers()) {
                    if (p.getSchool().getStudentsInDiningRoom(color) >= 3)
                        p.getSchool().setStudentsInDiningRoom(color, p.getSchool().getStudentsInDiningRoom(color) - 3);
                    else
                        p.getSchool().setStudentsInDiningRoom(color, 0);
                }
                break;
            }
        }
    }
}
