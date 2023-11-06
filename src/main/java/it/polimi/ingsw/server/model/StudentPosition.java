package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * an abstract class that implements all the method used for student locations change
 */
public abstract class StudentPosition implements Serializable {
    private List<Color> students;
    private int maxNumber;


    public void init(){
        students = new ArrayList<>();
    }
    /**
     * request for students in a place
     * @return a list of students
     */
    public List<Color> getStudents()
    {
        return students;
    }

    /**
     * request for the max number of students in a place
     * @return the max number of students in a place
     */
    public int getMaxNumber()
    {
        return maxNumber;
    }

    /**
     * set the max number of students in a place
     * @param maxNumber of students in a place
     */
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * method that adds students to the new position
     * @param newStudents list of students to add
     */
    public void addStudents (List<Color> newStudents) {
        if (students==null)
            students = new ArrayList<>();
        if (students.size() + newStudents.size() <= maxNumber)
            students.addAll(newStudents);
    }

    /**
     * method that adds a student to the new position
     * @param newStudent the color of the student to add
     */
    public void addStudent (Color newStudent) {
        if (students==null)
            students = new ArrayList<>();
        if (students.size() + 1 <= maxNumber)
            students.add(newStudent);
    }

    /**
     * method that removes students from the old position
     * @param oldStudents list of students to remove
     */
    public void removeStudents (List<Color> oldStudents){
        if (students.containsAll(oldStudents))
            students.removeAll(oldStudents);
    }

    /**
     * method that removes a student from the old position
     * @param oldStudent the color of the student to remove
     */
    public void removeStudent (Color oldStudent){
        students.remove(oldStudent);
    }
}
