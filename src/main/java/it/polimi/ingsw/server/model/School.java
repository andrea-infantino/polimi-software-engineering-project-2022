package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represent the school of each player
 */
public class School extends StudentPosition {
    private int playerId;
    private Map<Color, Integer> diningRoom;
    public Map<Color, Boolean> professor;

    /**
     * Class school constructor
      * @param playerId current Player Id
     */
    public School(int playerId){
        this.playerId = playerId;
        super.init();
        super.setMaxNumber(9);
        diningRoom = new HashMap<>();
        professor = new HashMap<>();
        for(Color c : Color.values()){
            diningRoom.put(c, 0);
            professor.put(c, Boolean.FALSE);
        }
    }

    /**
     * Request for playerId
     * @return playerId current Player
     */
    public int getPlayerId(){
        return playerId;
    }

    /**
     * Request for the student numbers in dining room
     * @param color that represent the color of the students i want to calculate
     * @return the student numbers of that color in  dining room
     */
    public int getStudentsInDiningRoom(Color color){
        return diningRoom.get(color);
    }

    public Map<Color, Integer> getDiningRoom() { return diningRoom; }

    /**
     * Request for add a student in dining room
     * @param color that represent the student color i want to add
     */
    public void addStudentInDiningRoom(Color color){
        diningRoom.put(color, diningRoom.get(color) + 1);
    }

    /**
     * Request for remove Students from dining room
     * @param oldStudents that represent the students i want to remove from dining room
     */
    public void removeStudentsInDiningRoom(List<Color> oldStudents){
        for(Color s : oldStudents)
            diningRoom.put(s, diningRoom.get(s) - 1);
    }

    /**
     * Request for set student color and student numbers in dining room
     * @param color that represent the color of students i want to set
     * @param n that represent the number of students i want to set
     */
    public void setStudentsInDiningRoom(Color color, int n) {
        diningRoom.put(color, n);
    }

    /**
     * Check if players have to receive a coin
     * @param color that represent which color i refer
     * @return true if the last student position in dining room is the third, sixth or ninth (in Expert Game)
     */
    public boolean needCoin(Color color){
        return (diningRoom.get(color) == 3 || diningRoom.get(color) == 6 || diningRoom.get(color) == 9);
    }

    /**
     * Check if the player has the professor of a particular color in his school
     * @param color professor's color that is needed to check
     * @return true if for that color there's a professor in school
     */
    public boolean hasProfessor(Color color){return professor.get(color);}
}


