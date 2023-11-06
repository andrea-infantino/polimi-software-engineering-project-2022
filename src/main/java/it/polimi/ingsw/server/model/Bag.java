package it.polimi.ingsw.server.model;

import it.polimi.ingsw.resources.enumerators.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * class that represent the bag where all student are at start of the game
 */
public class Bag extends StudentPosition {

    /**
     * constructor
     */
    public Bag() {
        super.setMaxNumber(120);
        int i;
        for(Color c : Color.values())
            for(i = 0; i<22; i++)
                super.addStudent(c);
    }

    /**
     * method that extracts the required number of students from the bag
     * @param n number of students to extract
     * @return the extracted students organized in a list
     */
    public List<Color> extract(int n) {
        List<Color> temp = new ArrayList<>();
        for(int i=0; i<n; i++) {
            int random = (int) (Math.random() * super.getStudents().size());
            temp.add(super.getStudents().get(random));
            super.removeStudent(super.getStudents().get(random));
        }
        return temp;
    }

    /**
     * method that extract a single student from the bag
     * @return one student's color
     */
    public Color extract() {
        int random = (int) (Math.random() * super.getStudents().size());
        Color returned = super.getStudents().get(random);
        super.removeStudent(super.getStudents().get(random));
        return returned;
    }

    /**
     * check if the bag is empty
     * @return true if the bag is empty, false otherwise
     */
    public boolean isEmpty(){ return super.getStudents().isEmpty(); }
}
