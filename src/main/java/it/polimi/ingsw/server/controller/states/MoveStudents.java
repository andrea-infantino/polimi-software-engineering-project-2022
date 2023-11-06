package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.resources.ActionPhase1Move;
import it.polimi.ingsw.resources.enumerators.Color;
import it.polimi.ingsw.resources.enumerators.Destination;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.GameControllerState;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.expert.ExpertPlayer;

import java.util.List;

/**
 * Class that represent students movement
 */
public class MoveStudents implements GameControllerState {

  @Override
  public void nextState(GameController g) {
      g.setState(new MoveMotherNature());
  }


  @Override
  public void Action(Object o_playerId, Object o_moves) {
    int playerId = (Integer) o_playerId;
    List<ActionPhase1Move> moves = (List<ActionPhase1Move>) o_moves;
    for(ActionPhase1Move move : moves){
      Server.game.getPlayerById(playerId).getSchool().removeStudent(move.getStudent());
      if(move.getDestination() == Destination.ISLAND)
        moveStudentOnIsland(move.getStudent(), move.getIslandId());
      else
        moveStudentInDiningRoom(move.getStudent(),playerId);
    }
  }

  /**
   * Represent the student movement from school to a particular island
   * @param student which student player wants to move
   * @param islandId island chosen by the player as the destination
   */
  public static void moveStudentOnIsland (Color student, int islandId){
    Server.game.getBoard().getIslandGroupById(islandId).addStudent(student);
  }

  /**
   * Represent the student movement from school to dining room
   * @param student which student player wants to move
   * @param playerId of the player that is moving his students
   */
  public static void moveStudentInDiningRoom (Color student,int playerId) {
    Server.game.getPlayerById(playerId).getSchool().addStudentInDiningRoom(student);
    moveProfessor(student, playerId);
    if (Server.game.isExpertMode())
      if (Server.game.getPlayerById(playerId).getSchool().needCoin(student))
        ((ExpertPlayer) Server.game.getPlayerById(playerId)).addCoin();

  }

  public static void moveProfessor(Color student, int playerId) {
    if (Server.game.getBoard().professors.get(student)) {
      Server.game.getBoard().professors.put(student, false);
      Server.game.getPlayerById(playerId).getSchool().professor.put(student, true);
    } else {
      int playerWithProfessor = -1;
      for (Player p : Server.game.getPlayers()) {
        if (p.getSchool().hasProfessor(student))
          playerWithProfessor = p.getId();
      }
      if (playerId != playerWithProfessor) {
        int numStudentsCurrentPlayer = Server.game.getPlayerById(playerId).getSchool().getStudentsInDiningRoom(student);
        int numStudentsPlayerWithProfessor = Server.game.getPlayerById(playerWithProfessor).getSchool().getStudentsInDiningRoom(student);
        if (numStudentsCurrentPlayer > numStudentsPlayerWithProfessor || (numStudentsCurrentPlayer == numStudentsPlayerWithProfessor && Server.game.currentRound.currentTurn.card2Played)) {
          Server.game.getPlayerById(playerWithProfessor).getSchool().professor.put(student, false);
          Server.game.getPlayerById(playerId).getSchool().professor.put(student, true);
        }
      }
    }
  }
}